# 國泰世華 JAVA Engineer 線上作業

使用 Maven 建置一個 Spring Boot 專案，並開發 API 與 DB 進行串接，實作以下內容，將結果上傳至 GitHub，並提供連結。

---

## ⚙️ 專案需求：

1. Build Tool：Maven
2. JDK：8
3. Spring-boot
4. 資料庫：H2（ORM 請使用 OpenJPA 或 Spring Data JPA）
    - 並提供測試資料相關的初始化 SQL

---

## 📌 功能簡述：

1. 呼叫 coindesk API，解析其下行內容並進行資料轉換，實作新的 API。
    - API 路徑：[https://kengp3.github.io/blog/coindesk.json](https://kengp3.github.io/blog/coindesk.json)

2. 建立一張幣別與其對應中文名稱的資料表（需附建立 SQL 語法），並提供：
    - 查詢 / 新增 / 修改 / 刪除 功能 API。

---

## 🛠️ 實作內容：

1. 幣別資料表 CRUD 等維護功能的 API。
2. 呼叫 coindesk 的 API。
3. 呼叫 coindesk 的 API，並進行資料轉換，組成新 API。

   此新 API 包含以下內容：  
   A. 更新時間（時間格式範例：1990/01/01 00:00:00）  
   B. 幣別相關資訊（幣別、幣別中文名稱，以及匯率）

---

## 🧪 請撰寫以下各項功能之測試：

1. 針對資料轉換相關邏輯作單元測試。
2. 測試呼叫幣別對應表資料 CRUD API，並顯示其內容。
3. 測試呼叫 coindesk API，並顯示其內容。
4. 測試呼叫資料轉換的 API，並顯示其內容。

---

## 🧑‍💻 專案結構
- src/main/java: 包含核心業務邏輯與 API 實作
- src/main/resources: 存放設定檔、資料庫初始化腳本等
- src/test/java: 單元測試與整合測試

---

## 📝 API 端點範例

1. 幣別資料 CRUD API
- GET /api/currencies: 查詢所有幣別
```
curl -X GET http://localhost:8080/api/currencies
```

- POST /api/currencies: 新增幣別資料
```
curl -X POST http://localhost:8080/api/currencies -H "Content-Type: application/json" -d '{"code": "USD", "nameZh": "美元", "nameEn": "US Dollar"}'
```

2. Coindesk API 數據
- GET /api/currencies/coindesk-data: 查詢 Coindesk 資料
```
curl -X GET http://localhost:8080/api/currencies/coindesk-data
```
