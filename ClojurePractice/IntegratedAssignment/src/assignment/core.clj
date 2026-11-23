
(ns assignment.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.util.response :refer [response status content-type]]
            [cheshire.core :as json])
  (:import [java.sql DriverManager]))

;; Database connection
(def db-url "jdbc:sqlite:sales.db")

;; Sample dataset
(def sample-sales
  [{:product "Laptop" :quantity 2 :price 50000.0}
   {:product "Mouse" :quantity 5 :price 500.0}
   {:product "Keyboard" :quantity 3 :price 1500.0}
   {:product "Monitor" :quantity 2 :price 12000.0}
   {:product "Headphones" :quantity 4 :price 2000.0}])

;; Create database tables
(defn create-tables! []
  (with-open [conn (DriverManager/getConnection db-url)
              stmt (.createStatement conn)]
    (.execute stmt
      "CREATE TABLE IF NOT EXISTS sales (
         id INTEGER PRIMARY KEY AUTOINCREMENT,
         product TEXT,
         quantity INTEGER,
         price REAL
       )")

    (.execute stmt
      "CREATE TABLE IF NOT EXISTS summaries (
         id INTEGER PRIMARY KEY,
         payload TEXT
       )")))

;; Insert sales into database
(defn insert-sales! [sales]
  (with-open [conn (DriverManager/getConnection db-url)
              stmt (.prepareStatement conn
                "INSERT INTO sales (product, quantity, price)
                 VALUES (?, ?, ?)")]
    (doseq [sale sales]
      (.setString stmt 1 (:product sale))
      (.setInt stmt 2 (:quantity sale))
      (.setDouble stmt 3 (:price sale))
      (.executeUpdate stmt))))

;; Read sales from database
(defn read-sales []
  (with-open [conn (DriverManager/getConnection db-url)
              stmt (.createStatement conn)
              rs (.executeQuery stmt "SELECT * FROM sales")]
    (loop [sales []]
      (if (.next rs)
        (recur
          (conj sales
            {:product (.getString rs "product")
             :quantity (.getInt rs "quantity")
             :price (.getDouble rs "price")}))
        sales))))

;; Initialize database and insert sample data once
(defn initialize-db! []
  (try
    (Class/forName "org.sqlite.JDBC")
    (create-tables!)
    (when (empty? (read-sales))
      (insert-sales! sample-sales))
    (println "Database initialized successfully.")

    (catch Exception e
      (println "Database initialization error:" (.getMessage e))
      (throw e))))

;; Process dataset using map, filter and reduce
(defn process-sales [sales]

  (let [processed
        (->> sales
             (map (fn [sale]
                    (assoc sale :total
                      (* (:quantity sale) (:price sale)))))
             (filter #(>= (:total %) 1000.0))
             vec)

        summary
        (reduce
          (fn [result sale]
            (-> result
                (update :orderCount inc)
                (update :revenue + (:total sale))))
          {:orderCount 0 :revenue 0.0}
          processed)]

    (assoc summary
      :averageOrderValue
      (if (pos? (:orderCount summary))
        (/ (:revenue summary) (:orderCount summary))
        0.0)
      :orders processed)))

;; Save processed summary to database
(defn save-summary! [summary]
  (with-open [conn (DriverManager/getConnection db-url)
              stmt (.prepareStatement conn
                "INSERT INTO summaries (id, payload)
                 VALUES (1, ?)
                 ON CONFLICT(id)
                 DO UPDATE SET payload = excluded.payload")]
    (.setString stmt 1 (json/generate-string summary))
    (.executeUpdate stmt)))

;; Read saved summary from database
(defn read-summary []
  (with-open [conn (DriverManager/getConnection db-url)
              stmt (.createStatement conn)
              rs (.executeQuery stmt
                "SELECT payload FROM summaries WHERE id = 1")]
    (when (.next rs)
      (json/parse-string (.getString rs "payload") true))))

;; Ring HTTP endpoint
(defn app [request]
  (if (and (= :get (:request-method request))
           (= "/api/summary" (:uri request)))

    (try
      (let [sales (read-sales)
            summary (process-sales sales)]

        (save-summary! summary)

        (-> (response (json/generate-string (read-summary)))
            (content-type "application/json")))

      (catch Exception e
        (-> (response
              (json/generate-string
                {:error "Unable to process request"
                 :details (.getMessage e)}))
            (status 500)
            (content-type "application/json"))))

    (-> (response
          (json/generate-string {:message "Endpoint not found"}))
        (status 404)
        (content-type "application/json"))))

;; Main function
(defn -main []
  (try
    (initialize-db!)
    (println "Starting Clojure Ring Server...")
    (println "API: http://localhost:3000/api/summary")
    (run-jetty app {:port 3000 :join? true})

    (catch Exception e
      (println "Application error:" (.getMessage e)))))
