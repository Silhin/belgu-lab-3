package ru.silhin.lab_econom.service;

import ru.silhin.lab_econom.model.ActionCreatingModel;
import ru.silhin.lab_econom.model.ActionWorkModel;
import ru.silhin.lab_econom.model.ConfigModel;
import ru.silhin.lab_econom.model.WorkModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PlanTimeService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static String service(List<ActionWorkModel> works, ConfigModel configModel) {
        StringBuilder html = new StringBuilder("""
                <h1>Разработка плана и графика разработки</h1>
                <h2>План разработки</h2>
                <table>
                  <caption>Таблица 2 - Комплекс работ по разработке проекта</caption>
                  <tr>
                    <th rowspan="2">Содержание работ</th>
                    <th rowspan="2">Исполнители</th>
                    <th rowspan="2">Длительность, дни</th>
                    <th colspan="2">Нагрузка</th>
                </tr>
                <tr>
                    <th>дни</th>
                    <th>%</th>
                </tr>
                """);

        Set<WorkModel> workSet = works.stream().map(ActionWorkModel::getWorkModel).collect(Collectors.toSet());
        for (WorkModel workModel : workSet) {
            List<ActionWorkModel> actions = works.stream().filter(o -> o.getWorkModel().equals(workModel)).toList();
            ActionWorkModel maxTimeModel = Collections.max(actions, Comparator.comparingInt(ActionWorkModel::getTime));

            StringBuilder actionsString = new StringBuilder();
            StringBuilder timesString = new StringBuilder();
            StringBuilder loadString = new StringBuilder();
            for (ActionWorkModel action : actions) {
                actionsString.append(action.getActionModel().getName()).append("<br/>");
                timesString.append(action.getTime()).append("<br/>");
                loadString.append((int) (action.getTime() / (double) maxTimeModel.getTime() * 100)).append("<br/>");
            }
            html.append(String.format("""
                    <tr>
                        <td>%s</td>
                        <td>%s</td>
                        <td>%s</td>
                        <td>%s</td>
                        <td>%s</td>
                    </tr>
                    """, workModel.toString(), actionsString, maxTimeModel.getTime(), timesString, loadString));
        }

        Map<ActionCreatingModel, Integer> times = new HashMap<>();
        for (ActionWorkModel actionWorkModel : works) {
            int time = times.getOrDefault(actionWorkModel.getActionModel(), 0);
            times.put(actionWorkModel.getActionModel(), time + actionWorkModel.getTime());
        }

        int maxTime = Collections.max(times.values());
        html.append(String.format("""
                                <tr>
                                    <td>Итого по проекту</td>
                                    <td>%s</td>
                                    <td>%s</td>
                                    <td>%s</td>
                                    <td>%s</td>
                                </tr>
                                </table>
                                """,
                concat(times.keySet().stream().map(ActionCreatingModel::getName).toList()), maxTime,
                concat(times.values().stream().map(String::valueOf).toList()),
                concat(times.values().stream().map(intt -> (int) (intt / (double) maxTime * 100)).map(String::valueOf).toList()))
        );

        html.append("""
                <h2>График разработки</h2>
                <table>
                    <caption>Таблица 3 - Календарный график выполнения работ</caption>
                    <tr>
                        <th rowspan="2">Содержание работы</th>
                        <th rowspan="2">Исполнители</th>
                        <th rowspan="2">Длительность, дни</th>
                        <th colspan="2">График работ</th>
                    </tr>
                    <tr>
                        <th>Начало</th>
                        <th>Конец</th>
                    </tr>
                """);

        LocalDate finalDay = configModel.getStartTime();
        for(WorkModel model : workSet) {
            List<ActionWorkModel> list = works.stream().filter(actionWorkModel -> actionWorkModel.getWorkModel().equals(model)).toList();
            html.append(String.format("""
                    <tr>
                        <td rowspan="%s">%s</td>
                    """, list.size(), model.getName())
            );

            boolean flag = false;
            LocalDate maxDay = finalDay;
            for(ActionWorkModel actionWorkModel : list) {
                if (flag) {
                    html.append("<tr>");
                } else {
                    flag = true;
                }

                LocalDate date = finalDay.plusDays(actionWorkModel.getTime());
                if(date.compareTo(maxDay) > 0) {
                    maxDay = date;
                }
                html.append(String.format("""
                            <td>%s</td>
                            <td>%s</td>
                            <td>%s</td>
                            <td>%s</td>
                        """,
                        actionWorkModel.getActionModel().getName(),
                        actionWorkModel.getTime(),
                        finalDay.format(FORMATTER),
                        date.format(FORMATTER))
                );
                html.append("</tr>");
            }
            finalDay = maxDay.plusDays(1);
        }
        html.append("</table>");
        configModel.setTimes(times);
        return html.toString();
    }

    private static String concat(List<String> strings) {
        StringBuilder str = new StringBuilder();
        strings.forEach(s -> str.append(s).append("<br/>"));
        return str.toString();
    }
}
