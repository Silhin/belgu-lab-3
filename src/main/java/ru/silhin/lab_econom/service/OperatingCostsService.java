package ru.silhin.lab_econom.service;

import ru.silhin.lab_econom.model.ActionOperationAnalogModel;
import ru.silhin.lab_econom.model.ActionOperationProjectModel;
import ru.silhin.lab_econom.model.AnalogCostModel;
import ru.silhin.lab_econom.model.ConfigModel;
import ru.silhin.lab_econom.model.MachineTaskModel;
import ru.silhin.lab_econom.model.MaterialModel;
import ru.silhin.lab_econom.model.OperationMachineModel;

import java.util.List;

public class OperatingCostsService {
    public static String service(List<ActionOperationProjectModel> projectList, List<AnalogCostModel> analogCostList,
                                 List<OperationMachineModel> machineList, List<MachineTaskModel> taskList,
                                 List<ActionOperationAnalogModel> analogList, ConfigModel config) {
        StringBuilder html = new StringBuilder();

        html.append("""
                <h1>Эксплуатационные затраты</h1>
                <h2>Заработные платы</h2>
                <table>
                    <caption>Таблица 10 - Данные по заработной плате специалистов (для проекта)</caption>
                    <tr>
                        <th>Должность</th>
                        <th>Должностной оклад, руб.</th>
                        <th>Средняя дневная ставка, руб./день</th>
                        <th>Затраты времени на эксплуатацию, человеко-дней</th>
                        <th>Фонд заработной платы, руб.</th>
                    </tr>
                """);

        double summ = 0;
        for (ActionOperationProjectModel model : projectList) {
            double amount = model.getTime() * (model.getSalary() / config.getWorkDaysInMonth())
                    * (1 + config.getSocialAmountCoeff()) * (1 + config.getAdditionalAmountCoeff());
            html.append(String.format("""
                            <tr>
                                <td>%s</td>
                                <td>%.2f</td>
                                <td>%.2f</td>
                                <td>%s</td>
                                <td>%s</td>
                            </tr>
                            """, model.getName(), model.getSalary(),
                    model.getSalary() / config.getWorkDaysInMonth(),
                    model.getTime(), amount)
            );
            summ += amount;
        }

        html.append(String.format("""
                <b>
                <tr>
                    <td colspan="4">ИТОГО</td>
                    <td>%.2f</td>
                </tr>
                </b>
                </table>
                """, summ)
        );
        config.setTotalAmountProjectOperation(summ);

        // Аналог зарплаты

        html.append("""
                <br/>
                <table>
                    <caption>Таблица 11 - Данные по заработной плате специалистов (для аналога)</caption>
                    <tr>
                        <th>Должность</th>
                        <th>Должностной оклад, руб.</th>
                        <th>Средняя дневная ставка, руб./день</th>
                        <th>Затраты времени на эксплуатацию, человеко-дней</th>
                        <th>Фонд заработной платы, руб.</th>
                    </tr>
                """);

        summ = 0;
        for (ActionOperationAnalogModel model : analogList) {
            double amount = model.getTime() * (model.getSalary() / config.getWorkDaysInMonth())
                    * (1 + config.getSocialAmountCoeff()) * (1 + config.getAdditionalAmountCoeff());
            html.append(String.format("""
                            <tr>
                                <td>%s</td>
                                <td>%.2f</td>
                                <td>%.2f</td>
                                <td>%s</td>
                                <td>%s</td>
                            </tr>
                            """, model.getName(), model.getSalary(),
                    model.getSalary() / config.getWorkDaysInMonth(),
                    model.getTime(), amount)
            );
            summ += amount;
        }

        html.append(String.format("""
                <b>
                <tr>
                    <td colspan="4">ИТОГО</td>
                    <td>%.2f</td>
                </tr>
                </b>
                </table>
                """, summ)
        );
        config.setTotalAmountAnalogOperation(summ);

        // Эксплуатационные затраты

        double amortAmountProject = 0;
        double amortAmountAnalog = 0;
        double powerAmountProject = 0;
        double powerAmountAnalog = 0;
        double repairAmountProject = 0;
        double repairAmountAnalog = 0;
        for(MaterialModel model : taskList.stream().map(MachineTaskModel::getMaterialModel).distinct().toList()) {
            double maxDays = taskList.stream()
                    .filter(m -> m.getMaterialModel().equals(model))
                    .map(MachineTaskModel::getDays)
                    .max(Integer::compareTo).orElse(0);
            double maxTime = taskList.stream()
                    .filter(m -> m.getMaterialModel().equals(model))
                    .map(MachineTaskModel::getTime)
                    .max(Double::compareTo).orElse(.0);
            OperationMachineModel machineModel = machineList.stream()
                    .filter(m -> m.getMaterialModel().equals(model))
                    .findFirst().orElse(null);

            double tempPower = 0;
            double temp = model.getCount() * model.getPrice() * maxTime;
            if(machineModel != null) {
                temp *= machineModel.getAmort();
                tempPower = machineModel.getPower() * machineModel.getPowerKoeff() * maxTime * config.getPowerTarif();
            }
            temp /= (maxDays * config.getNormWorkTime());
            double tempRepair = config.getRepairNormPercent() * maxTime * model.getPrice() / (maxDays * config.getNormWorkTime());

            if(model.getType().equals("Проект")) {
                amortAmountProject += temp;
                powerAmountProject += tempPower;
                repairAmountProject += tempRepair;
            } else {
                amortAmountAnalog += temp;
                powerAmountAnalog += tempPower;
                repairAmountAnalog += tempRepair;
            }
        }

        double materialAmountProject = config.getMaterialPercent() * taskList.stream()
                .map(MachineTaskModel::getMaterialModel)
                .distinct().mapToDouble(MaterialModel::getCount).sum();
        double materialAmountAnalog = config.getMaterialPercent() * analogCostList.stream()
                .filter(analogCostModel -> analogCostModel.getName().equals(""))
                .map(AnalogCostModel::getCost)
                .findAny().orElse(.0);
        double overheadAmountProject = config.getOverheadPercent() * (config.getTotalAmountProjectOperation() + amortAmountProject + powerAmountProject + repairAmountProject + materialAmountProject);
        double overheadAmountAnalog = config.getOverheadPercent() * (config.getTotalAmountAnalogOperation() + amortAmountAnalog + powerAmountAnalog + repairAmountAnalog + materialAmountAnalog);
        double totalProject = (overheadAmountProject + config.getTotalAmountProjectOperation() + amortAmountProject + powerAmountProject + repairAmountProject + materialAmountProject);
        double totalAnalog = (overheadAmountAnalog + config.getTotalAmountAnalogOperation() + amortAmountAnalog + powerAmountAnalog + repairAmountAnalog + materialAmountAnalog);

        html.append(String.format("""
                        <h2>Эксплуатационные затраты</h2>
                        <table>
                            <caption>Таблица 12 - Годовые эксплуатационные затраты</caption>
                            <tr>
                                <th>Статьи затрат</th>
                                <th>Затраты на проект, руб.</th>
                                <th>Затраты на аналог, руб.</th>
                            </tr>
                            <tr>
                                <td>Основная и дополнительная зарплата с отчислениями</td>
                                <td>%.2f</td>
                                <td>%.2f</td>
                            </tr>
                            <tr>
                                <td>Амортизационные отчисления</td>
                                <td>%.2f</td>
                                <td>%.2f</td>
                            </tr>
                            <tr>
                                <td>Затраты на электроэнергию</td>
                                <td>%.2f</td>
                                <td>%.2f</td>
                            </tr>
                            <tr>
                                <td>Затраты на текущий ремонт</td>
                                <td>%.2f</td>
                                <td>%.2f</td>
                            </tr>
                            <tr>
                                <td>Затраты на материалы</td>
                                <td>%.2f</td>
                                <td>%.2f</td>
                            </tr>
                            <tr>
                                <td>Накладные расходы</td>
                                <td>%.2f</td>
                                <td>%.2f</td>
                            </tr>
                            <b>
                            <tr>
                                <td>ИТОГО</td>
                                <td>%.2f</td>
                                <td>%.2f</td>
                            </tr>
                            </b>
                        </table>
                        """,
                config.getTotalAmountProjectOperation(), config.getTotalAmountAnalogOperation(),
                amortAmountProject, amortAmountAnalog,
                powerAmountProject, powerAmountAnalog,
                repairAmountProject, repairAmountAnalog,
                materialAmountProject, materialAmountAnalog,
                overheadAmountProject, overheadAmountAnalog,
                totalProject, totalAnalog)
        );

        return html.toString();
    }
}
