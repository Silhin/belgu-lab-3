package ru.silhin.lab_econom.service;

import ru.silhin.lab_econom.model.ActionCreatingModel;
import ru.silhin.lab_econom.model.AnalogCostModel;
import ru.silhin.lab_econom.model.ConfigModel;
import ru.silhin.lab_econom.model.CostMachineTimeModel;
import ru.silhin.lab_econom.model.MachineTaskModel;
import ru.silhin.lab_econom.model.MaterialModel;
import ru.silhin.lab_econom.model.OtherCostModel;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreatingProjectService {
    public static String service(List<MaterialModel> materialList, List<MachineTaskModel> machineTasks,
                                 List<OtherCostModel> costList, List<AnalogCostModel> analogCostList,
                                 CostMachineTimeModel machineTime, ConfigModel config) {
        StringBuilder html = new StringBuilder();
        html.append("""
                <h1>Затраты на разработку проекта</h1>
                <h2>Заработная плата разработчикам</h2>
                <table>
                    <caption>Таблица 4 - Основная заработная плата разработчиков</caption>
                    <tr>
                        <th>Должность</th>
                        <th>Должностной оклад, руб.</th>
                        <th>Средняя дневная ставка, руб.</th>
                        <th>Затраты времени на разработку, человеко-дней</th>
                        <th>Основная заработная плата, руб.</th>
                    </tr>
                """);

        double summ = 0;
        int days = config.getWorkDaysInMonth();
        for (ActionCreatingModel model : config.getTimes().keySet()) {
            double salaryPerDay = model.getSalary() / days;
            int time = config.getTimes().get(model);
            html.append(String.format("""
                        <tr>
                            <td>%s</td>
                            <td>%.2f</td>
                            <td>%.2f</td>
                            <td>%s</td>
                            <td>%.2f</td>
                        </tr>
                    """, model.getName(), model.getSalary(), salaryPerDay, time, salaryPerDay * time));
            summ += salaryPerDay * time;
        }
        html.append(String.format("""
                <b>
                <tr>
                    <td colspan="4">ИТОГО</td>
                    <td>%.2f</td>
                </tr>
                </b>
                </table>
                """, summ));

        // Материал

        html.append("""
                <h2>Затраты на материалы</h2>
                <table>
                    <caption>Таблица 5 - Затраты на материалы</caption>
                    <tr>
                        <th>Материал</th>
                        <th>Требуемое количество, шт.</th>
                        <th>Цена за единицу, руб.</th>
                        <th>Сумма, руб.</th>
                    </tr>
                """);

        double materialSumm = 0;
        for (MaterialModel model : materialList) {
            html.append(String.format("""
                    <tr>
                        <td>%s</td>
                        <td>%s</td>
                        <td>%.2f</td>
                        <td>%.2f</td>
                    </tr>
                    """, model.getName(), model.getCount(), model.getPrice(), model.getCount() * model.getPrice()));
            materialSumm += model.getCount() * model.getPrice();
        }
        html.append(String.format("""
                <b>
                <tr>
                    <td colspan="3">ИТОГО</td>
                    <td>%.2f</td>
                </tr>
                </b>
                </table>
                """, materialSumm));

        // Смета

        double additional = config.getAdditionalAmountCoeff() * summ;
        double social = config.getSocialAmountCoeff() * summ;
        double overhead = config.getOverheadCostsCoeff() * summ;
        double machine = machineTime.getTime() * machineTime.getPrice() * machineTime.getKoeff();
        config.setTotalAmountProject(summ + additional + social + overhead + machine + materialSumm);
        html.append(String.format("""
                <h2>Затраты на разработку проекта</h2>
                <table>
                    <caption>Таблица 6 - Затраты на разработку</caption>
                    <tr>
                        <th>Статьи затрат</th>
                        <th>Сумма, руб.</th>
                    </tr>
                    <tr>
                        <td>Основная заработная плата</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Дополнительная зарплата</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Отчисления на социальные нужны</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Затраты на материалы</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Затраты на машинное время</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Накладные расходы организации</td>
                        <td>%.2f</td>
                    </tr>
                    <b>
                    <tr>
                        <td>ИТОГО</td>
                        <td>%.2f</td>
                    </tr>
                    </b>
                </table>
                """, summ, additional, social, materialSumm, machine, overhead, config.getTotalAmountProject())
        );

        // Капитальные вложения на реализацию

        double Ko = 0;
        Set<MaterialModel> machineSet = machineTasks.stream().map(MachineTaskModel::getMaterialModel).collect(Collectors.toSet());
        for (MaterialModel model : machineSet) {
            double temp = model.getPrice() * model.getCount();

            List<MachineTaskModel> currentMachineTasks = machineTasks.stream().filter(m -> m.getMaterialModel().equals(model)).toList();
            int maxTime = currentMachineTasks.stream().map(MachineTaskModel::getDays).max(Integer::compareTo).orElse(0);
            double T = 0;
            for (MachineTaskModel task : currentMachineTasks) {
                T += task.getTime() * task.getDays();
            }
            temp *= T / (double) maxTime;

            Ko += temp;
        }
        html.append(String.format("""
                <h2>Затраты на реализацию</h2>
                <table>
                    <caption>Таблица 7 - Затраты на реализацию</caption>
                    <tr>
                        <th>Статья затрат</th>
                        <th>Сумма, руб</th>
                    </tr>
                    <tr>
                        <td>Затраты на оборудование и материалы</td>
                        <td>%.2f</td>
                    </tr>
                """, Ko)
        );
        summ = Ko;
        for (OtherCostModel model : costList) {
            html.append(String.format("""
                    <tr>
                        <td>%s</td>
                        <td>%.2f</td>
                    </tr>
                    """, model.getName(), model.getCost())
            );
            summ += model.getCost();
        }
        html.append(String.format("""
                    <b>
                    <tr>
                        <td>ИТОГО</td>
                        <td>%.2f</td>
                    </tr>
                    </b>
                </table>
                """, summ));

        // Итог разработки

        html.append(String.format("""
                <h2>Итоговые затраты на разработку проекта</h2>
                <table>
                    <caption>Таблица 8 - Суммарные затраты на разработку проекта</caption>
                    <tr>
                        <th>Тип вложения</th>
                        <th>Сумма, руб.</th>
                    </tr>
                    <tr>
                        <td>Капитальные вложения на проектирование</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Капитальные вложения на реализацию</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>ИТОГО</td>
                        <td>%.2f</td>
                    </tr>
                </table>
                """, config.getTotalAmountProject(), summ, config.getTotalAmountProject() + summ));
        config.setTotalAmountProject(config.getTotalAmountProject() + summ);

        // Аналог

        html.append("""
                <h2>Затраты на внедрение аналога</h2>
                <table>
                    <caption>Таблица 9 - Суммарные затраты на внедрение аналога</caption>
                    <tr>
                        <th>Тип вложения</th>
                        <th>Сумма, руб.</th>
                    </tr>
                """);

        summ = 0;
        for (AnalogCostModel costModel : analogCostList) {
            html.append(String.format("""
                    <tr>
                       <td>%s</td>
                       <td>%.2f</td>
                    </tr>
                    """, costModel.getName(), costModel.getCost())
            );
            summ += costModel.getCost();
        }
        html.append(String.format("""
                    <b>
                    <tr>
                        <td>ИТОГО</td>
                        <td>%.2f</td>
                    </tr>
                    </b>
                </table>
                """, summ)
        );
        config.setTotalAmountAnalog(summ);
        return html.toString();
    }
}
