package Exceptions;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Data_identification {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: Фамилия Имя Отчество дата_рождения номер_телефона пол");
        String input = scanner.nextLine();

        try {
            String[] data = input.split(" ");
            Data_identification.validateData(data);
            Data_identification.saveToFile(data);
            System.out.println("Данные успешно сохранены в файл.");
        } catch (InputDataException | IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void validateData(String[] data) throws InputDataException {
        if (data.length != 6) {
            throw new InputDataException("Введено неверное количество данных. Ожидается 6 значений.");
        }

        try {
            // Проверяем дату рождения на корректность формата
            LocalDate.parse(data[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        } catch (Exception e) {
            throw new InputDataException("Неверный формат даты рождения. Ожидается формат dd.mm.yyyy");
        }

        try {
            // Проверяем номер телефона на корректность формата
            Long.parseLong(data[4]);
        } catch (NumberFormatException e) {
            throw new InputDataException("Неверный формат номера телефона. Ожидается целое беззнаковое число.");
        }

        // Проверяем пол
        if (!data[5].equalsIgnoreCase("m") && !data[5].equalsIgnoreCase("f")) {
            throw new InputDataException("Неверный формат пола. Ожидается символ 'm' или 'f'.");
        }
    }

    public static void saveToFile(String[] data) throws IOException {
        String filename = data[0] + ".txt"; // Имя файла - фамилия
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(String.join(" ", data));
        }
    }
}