package ru.silhin.lab_econom.service;

import ru.silhin.lab_econom.model.QualityAnalogModel;
import ru.silhin.lab_econom.model.QualityProjectModel;

import java.util.Comparator;
import java.util.List;

public class KTYService {

    public static KTYResult service(List<QualityProjectModel> project, List<QualityAnalogModel> analog) {
        StringBuilder html = new StringBuilder("""
                        <h1>Расчет КТУ</h1>
                        <table>
                          <caption>Таблица 1 - Расчет показателя качества балльно-индексным методом</caption>
                          <tr>
                            <th rowspan="2">Показатели качества</th>
                            <th rowspan="2">Коэффициент весомости, B<sub>j</sub></th>
                            <th colspan="2">Проект</th>
                            <th colspan="2">Аналог</th>
                          </tr>
                          <tr>
                            <th>X<sub>j</sub></th>
                            <th>B<sub>j</sub> x X<sub>j</sub></th>
                            <th>X<sub>j</sub></th>
                            <th>B<sub>j</sub> x X<sub>j</sub></th>
                          </tr>
                """);

        double projectSumm = 0;
        double analogSumm = 0;
        project = project.stream().sorted(Comparator.comparing(o -> o.getQualityModel().getName())).toList();
        analog = analog.stream().sorted(Comparator.comparing(o -> o.getQualityModel().getName())).toList();
        for(int i = 0; i < project.size(); ++i) {
            QualityProjectModel projectModel = project.get(i);
            QualityAnalogModel analogModel = analog.get(i);

            double scoreP = projectModel.getQualityModel().getKoeff() * projectModel.getScore();
            double scoreA = analogModel.getQualityModel().getKoeff() * analogModel.getScore();
            html.append(String.format("""
                    <tr>
                        <td>%s</td>
                        <td>%.3f</td>
                        <td>%s</td>
                        <td>%.3f</td>
                        <td>%s</td>
                        <td>%.3f</td>
                    </tr>
                    """,
                    projectModel.getQualityModel().getName(),
                    projectModel.getQualityModel().getKoeff(),
                    projectModel.getScore(), scoreP,
                    analogModel.getScore(), scoreA
                    ));
            projectSumm += scoreP;
            analogSumm += scoreA;
        }
        double aK = projectSumm / analogSumm;
        html.append(String.format("""
                    <tr>
                        <td colspan="2"><b>Обобщенный показатель качества J<sub>ЭТУ</sub></b></td>
                        <td colspan="2">J<sub>ЭТУ1</sub> = %.3f</td>
                        <td colspan="2">J<sub>ЭТУ2</sub> = %.3f</td>
                    </tr>
                    <tr>
                        <td colspan="2">%s</td>
                        <td colspan="4">A<sub>k</sub> = %.3f</td>
                    </tr>
                    </table>
                """, projectSumm, analogSumm,
                aK > 1 ? "Разработка оправдана. A<sub>k</sub> больше 1!" : "Разработка не оправдана. A<sub>k</sub> меньше или равен 1!", aK)
        );
        return new KTYResult(html.toString(), aK);
    }

    public record KTYResult(String html, Double ak) {}

}
