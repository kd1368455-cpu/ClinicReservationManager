package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Reservation {
    private int id;
    private String patientName;
    private String date;
    private String time;

    public Reservation(int id, String patientName, String date, String time) {
        this.id = id;
        this.patientName = patientName;
        this.date = date;
        this.time = time;
    }

    public int getId() { return id; }
    public String getPatientName() { return patientName; }
    public String getDate() { return date; }
    public String getTime() { return time; }

    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }

    @Override
    public String toString() {
        return "予約ID: " + id + " | 患者名: " + patientName + " | 日付: " + date + " | 時間: " + time;
    }

    public String toCSV() {
        return id + "," + patientName + "," + date + "," + time;
    }

    public static Reservation fromCSV(String line) {
        String[] parts = line.split(",");
        return new Reservation(
            Integer.parseInt(parts[0]),
            parts[1],
            parts[2],
            parts[3]
        );
    }
}

public class ClinicReservationManager {
	private static List<Reservation> reservations = new ArrayList<>();
    private static int nextId = 1;
    private static final String FILE_NAME = "reservations.csv";

	public static void main(String[] args) {
		loadFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Clinic Reservation Manager ===");
            System.out.println("1. 予約追加");
            System.out.println("2. 予約一覧");
            System.out.println("3. 予約検索");
            System.out.println("4. 予約更新");
            System.out.println("5. 予約削除");
            System.out.println("6. 保存して終了");
            System.out.print("選択: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addReservation(scanner);
                case 2 -> listReservations();
                case 3 -> searchReservation(scanner);
                case 4 -> updateReservation(scanner);
                case 5 -> deleteReservation(scanner);
                case 6 -> {
                    saveToFile();
                    System.out.println("保存して終了します。");
                    return;
                }
                default -> System.out.println("無効な入力です。");
            }
        }
    }
	private static void addReservation(Scanner scanner) {
        System.out.print("患者名: ");
        String name = scanner.nextLine();

        System.out.print("日付 (例: 2026-05-03): ");
        String date = scanner.nextLine();

        System.out.print("時間 (例: 14:00): ");
        String time = scanner.nextLine();

        Reservation r = new Reservation(nextId++, name, date, time);
        reservations.add(r);

        System.out.println("予約を追加しました。");
    }

    private static void listReservations() {
        if (reservations.isEmpty()) {
            System.out.println("予約はありません。");
            return;
        }

        System.out.println("\n--- 予約一覧 ---");
        reservations.forEach(System.out::println);
    }

    private static void searchReservation(Scanner scanner) {
        System.out.print("検索する患者名: ");
        String keyword = scanner.nextLine();

        System.out.println("\n--- 検索結果 ---");
        boolean found = false;

        for (Reservation r : reservations) {
            if (r.getPatientName().contains(keyword)) {
                System.out.println(r);
                found = true;
            }
        }

        if (!found) {
            System.out.println("該当する予約がありません。");
        }
    }

    private static void updateReservation(Scanner scanner) {
        System.out.print("更新する予約ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Reservation target = findReservation(id);
        if (target == null) {
            System.out.println("該当する予約がありません。");
            return;
        }

        System.out.print("新しい患者名: ");
        target.setPatientName(scanner.nextLine());

        System.out.print("新しい日付: ");
        target.setDate(scanner.nextLine());

        System.out.print("新しい時間: ");
        target.setTime(scanner.nextLine());

        System.out.println("予約を更新しました。");
    }

    private static void deleteReservation(Scanner scanner) {
        System.out.print("削除する予約ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Reservation target = findReservation(id);
        if (target == null) {
            System.out.println("該当する予約がありません。");
            return;
        }

        reservations.remove(target);
        System.out.println("予約を削除しました。");
    }

    private static Reservation findReservation(int id) {
        return reservations.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private static void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Reservation r : reservations) {
                pw.println(r.toCSV());
            }
        } catch (IOException e) {
            System.out.println("保存中にエラーが発生しました。");
        }
    }

    private static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Reservation r = Reservation.fromCSV(line);
                reservations.add(r);
                nextId = Math.max(nextId, r.getId() + 1);
            }
        } catch (IOException e) {
            System.out.println("読み込み中にエラーが発生しました。");
        }
    }
		

	}


