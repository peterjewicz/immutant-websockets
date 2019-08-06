(ns immutant-websockets.core
    (:require [reagent.core :as reagent :refer [atom]]
              [wscljs.client :as ws]
              [wscljs.format :as fmt]))

(enable-console-print!)

(println "This text is printed from src/immutant-websockets/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload
; WEBSOCKET CODE AFTER This
(defonce input (.getElementById js/document "input"))
(defonce sendBtn (.getElementById js/document "send"))
(defonce closeBtn (.getElementById js/document "close"))
(defonce messages (.getElementById js/document "messages"))

(defn handle-onOpen []
  (js/alert "Connection Opened"))
(defn handle-onClose [])
(defn handle-onMessage [e]
  (js/console.log e))

(def handlers {:on-message (fn [e] (handle-onMessage e))
               :on-open    #(handle-onOpen)
               :on-close   #(handle-onClose)})

(defn open-ws-connection []
  (def socket (ws/create "ws://localhost:8080" handlers)))

(defn send-message []
  (ws/send socket "value" fmt/json))

(defn close-connection []
  (ws/close socket)
  (js/alert "Connection Closed"))


(defonce app-state (atom {:text "Hello world!"}))

(defn hello-world []
  [:div
    [:input#input {:type "Text" :placeholder "Enter Text To Reverse"}]
    [:div
      [:button#open {:onClick #(open-ws-connection)} "Open"]
      [:button#send {:onClick #(send-message)} "send"]
      [:button#close {:onClick #(close-connection)} "close"]]
    [:div#messages]])

(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)