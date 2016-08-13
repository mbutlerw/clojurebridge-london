(ns clojurebridge-london.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.form :refer :all]
            [hiccup.page :refer :all]))

(defn labeled-radio [label]
  [:label (radio-button {:ng-model "user.gender"} "user.gender" false label)
 (str label "    ")])

(defn register-teacher-form [request]
  (html5
    [:div
   [:form {:novalidate "" :role "form"}
    [:div {:class "form-group"}
     (label {:class "control-label"} "email" "Email")
     (email-field {:class "form-control" :placeholder "Email" :ng-model "user.email"} "user.email")]
    [:div {:class "form-group"}
     (label {:class "control-label"} "password" "Password")
     (password-field {:class "form-control" :placeholder "Password" :ng-model "user.password"} "user.password")]
    [:div {:class "form-group"}
     (label {:class "control-label"} "gender" "Gender")
     (reduce conj [:div {:class "btn-group"}] (map labeled-radio ["male" "female" "other"]))]
    [:div {:class "form-group"}
     [:label
      (check-box {:ng-model "user.remember"} "user.remember-me") " Remember me"]]]
   [:pre "form = {{ user | json }}"]]))

(defn hello
  [request]
  (str "Your name is: " (get-in request [:params :name])))

(def registered-teachers
  {:jr0cket {:language "clojure" :devtools ["emacs" "lighttable" "atom"] :github-pro true}
   :mbutlerw {:language ["JavaScript" "Ruby"] :devtools ["atom"] :github-pro true} })

(defn teachers
  "Get teachers by Github handles"
  [request]
  (let [teachers (map first registered-teachers)]
    (str "Registered Teachers are: " (apply str teachers))))


;; ((teachers :jr0cket) :language)
(get-in teachers [:jr0cket :language])

#_(defn add-teacher
  "Adds a teacher to the current list and returns all teachers"
  [request]
  (let [teacher ,,,,]
   (cons teachers teacher)))


(defroutes app-routes
  (route/resources "/")
  (GET "/" [] "Hello Clojure World")
  (GET "/teachers" [] teachers)
  (GET "/teachers/new" [] register-teacher-form)
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
