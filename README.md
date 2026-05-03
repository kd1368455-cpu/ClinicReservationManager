# ClinicReservationManager
Javaで作成した医療クリニック向け予約管理システムです。予約の追加・一覧表示・検索・更新・削除に加え、CSVファイルへの保存と読み込みに対応しており、業務システムの基本機能を一通り実装しています。

## 使用技術
- Java 17
- Eclipse
- CSV 入出力（FileWriter / FileReader / BufferedReader）
- コレクションAPI（List / ArrayList）

## 機能一覧
- 予約の新規追加
- 予約一覧表示
- 患者名による予約検索
- 予約内容の更新
- 予約削除
- CSVファイルへの保存
- CSVファイルからの読み込み

  ## プロジェクト構成
ClinicReservationManager/
├─ src/
│   └─ main/
│        └─ ClinicReservationManager.java
├─ reservations.csv   // 実行後に自動生成
└─ README.md

## 実行方法
1. Eclipse にプロジェクトをインポート
2. `ClinicReservationManager.java` を実行
3. コンソールメニューから操作できます

## 作成目的
医療・公共・業務システムなど幅広い分野を扱う企業である北都システム様の開発領域を意識し、
業務システムの基本機能（CRUD・検索・データ保存）を備えた予約管理システムを制作しました。

実務で頻繁に利用される「データ管理」「検索」「CSV入出力」を実装し、
業務ロジックの理解と基礎的なアプリケーション開発能力を示すことを目的としています。
