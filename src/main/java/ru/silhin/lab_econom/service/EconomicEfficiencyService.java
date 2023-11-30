package ru.silhin.lab_econom.service;

import ru.silhin.lab_econom.model.ConfigModel;

public class EconomicEfficiencyService {
    public static String service(ConfigModel configModel) {
        StringBuilder html = new StringBuilder();

        double amountWorkProject = configModel.getTotalAmountProjectOperation() + configModel.getKoeffEconomEffect()
                * configModel.getTotalAmountProject();
        double amountWorkAnalog =  configModel.getTotalAmountAnalogOperation() + configModel.getKoeffEconomEffect()
                * configModel.getTotalAmountAnalog();
        double economEffect = amountWorkProject * configModel.getAk() - amountWorkAnalog;

        html.append(String.format("""
                <h1>Показатели экономической эффективности</h1>
                <table>
                    <caption>Таблица 13 - Экономический эффект</caption>
                    <tr>
                        <th rowspan="2">Характеристика</th>
                        <th colspan="2">Значения</th>
                    </tr>
                    <tr>
                        <th>Разрабатываемый продукт</th>
                        <th>Продукт - Аналог</th>
                    </tr>
                    <tr>
                        <td>Себестоимость, руб.</td>
                        <td>%.2f</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Суммарные затраты, связанные с внедрением проекта, руб.</td>
                        <td>%.2f</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Приведенные затраты на единицу работ, руб.</td>
                        <td>%.2f</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Экономический эффект от использования разрабатываемой системы, руб.</td>
                        <td colspan="2">%.2f</td>
                    </tr>
                </table>
                """,
                configModel.getTotalAmountProjectOperation(), configModel.getTotalAmountAnalogOperation(),
                configModel.getTotalAmountProject(), configModel.getTotalAmountAnalog(),
                amountWorkProject, amountWorkAnalog, economEffect)
        );

        double paybackTime = configModel.getTotalAmountProject() / economEffect;
        html.append(String.format("""
                <h2>Экономическое обоснование</h2>
                <table>
                    <caption>Таблица 14 - Результаты экономического обоснования проекта</caption>
                    <tr>
                        <th>Характеристика проекта</th>
                        <th>Значение</th>
                    </tr>
                    <tr>
                        <td>Затраты на разработку и внедрение проекта, руб.</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Общие эксплуатационные затраты, руб.</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Экономический эффект, руб.</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Коэффициент экономической эффективности</td>
                        <td>%.2f</td>
                    </tr>
                    <tr>
                        <td>Срок окупаемости, лет</td>
                        <td>%.1f</td>
                    </tr>
                </table>
                """,
                configModel.getTotalAmountProject(),
                configModel.getTotalAmountProjectOperation(),
                economEffect, 1/paybackTime, paybackTime)
        );
        return html.toString();
    }
}
