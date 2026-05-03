package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MedicalRecordManager {
	// 患者リスト
    private static List<Patient> patients = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		while (true) {
            System.out.println("\n=== Medical Record Manager ===");
            System.out.println("1. 患者を登録");
            System.out.println("2. 患者一覧を表示");
            System.out.println("3. 診療記録を追加");
            System.out.println("4. CSVに保存");
            System.out.println("5. 終了");
            System.out.print("選択してください: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 改行消費

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    showPatients();
                    break;
                case 3:
                    addRecord();
                    break;
                case 4:
                    saveToCSV();
                    break;
                case 5:
                    System.out.println("終了します。");
                    return;
                default:
                    System.out.println("無効な入力です。");
            }
        }
    }

    // 患者登録
    private static void addPatient() {
        System.out.print("患者名: ");
        String name = scanner.nextLine();

        System.out.print("年齢: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("性別: ");
        String gender = scanner.nextLine();

        Patient p = new Patient(name, age, gender);
        patients.add(p);

        System.out.println("患者を登録しました。");
    }

    // 患者一覧
    private static void showPatients() {
        if (patients.isEmpty()) {
            System.out.println("登録された患者はいません。");
            return;
        }

        System.out.println("\n--- 患者一覧 ---");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i));
        }
    }

    // 診療記録追加
    private static void addRecord() {
        if (patients.isEmpty()) {
            System.out.println("患者がいません。先に登録してください。");
            return;
        }

        showPatients();
        System.out.print("診療記録を追加する患者番号: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        if (index < 0 || index >= patients.size()) {
            System.out.println("無効な番号です。");
            return;
        }

        System.out.print("診療内容: ");
        String detail = scanner.nextLine();

        patients.get(index).addRecord(detail);
        System.out.println("診療記録を追加しました。");
    }

    // CSV保存
    private static void saveToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("medical_records.csv"))) {
            writer.write("名前,年齢,性別,診療記録\n");

            for (Patient p : patients) {
                writer.write(p.toCSV() + "\n");
            }

            System.out.println("CSVに保存しました。");
        } catch (IOException e) {
            System.out.println("保存中にエラーが発生しました。");
        }
    }
}


// 患者クラス
class Patient {
    private String name;
    private int age;
    private String gender;
    private List<String> records = new ArrayList<>();

    public Patient(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public void addRecord(String record) {
        records.add(record);
    }

    public String toCSV() {
        return name + "," + age + "," + gender + "," + String.join(" / ", records);
    }

    @Override
    public String toString() {
        return name + "（" + age + "歳, " + gender + "） 記録数: " + records.size();
    }

}


