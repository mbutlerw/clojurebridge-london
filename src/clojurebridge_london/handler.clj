(ns clojurebridge-london.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.form :refer :all]
            [hiccup.page :refer :all]
            [ring.util.response :refer [redirect]]
            [ring.handler.dump :refer [handle-dump]]))

;; http://localhost:3000/hello?name="john"
;; {:params {:name "john"}}

(defn hello
  [request]
  (str "Your name is: " (get-in request [:params :name])))

(defn labeled-radio [label]
  [:label (radio-button {:ng-model "user.gender"} "user.gender" false label)
   (str label "    ")])

#_(defn register-teacher-form
  [request]
  (html5 [:div
          [:form {:novalidate "" :role "form"}
           [:div {:class "form-group"}
            (label {:class "control-label"} "github" "Github")
            (email-field {:class "form-control" :placeholder "Github" :ng-model "user.github"} "user.github")]
           [:div {:class "form-group"}
            (label {:class "control-label"} "email" "Email")
            (email-field {:class "form-control" :placeholder "Email" :ng-model "user.email"} "user.email")]
           [:div {:class "form-group"}
            (label {:class "control-label"} "password" "Password")
            (password-field {:class "form-control" :placeholder "Password" :ng-model "user.password"} "user.password")]
           [:div {:class "form-group"}
            (label {:class "control-label"} "devtools" "DevTools")
            (reduce conj [:div {:class "btn-group"}] (map labeled-radio ["emacs" "lighttable" "nightcode" "cursive/intelij" "atom/protorepl"]))]
           [:div {:class "form-group"}
            (label {:class "control-label"} "gender" "Gender")
            (reduce conj [:div {:class "btn-group"}] (map labeled-radio ["male" "female" "other"]))]
            [:div {:class "form-group"}
             [:label
              (check-box {:ng-model "user.remember"} "user.remember-me") " Remember me"]]]
          [:pre "form = {{ user | json }}"]]))


(defonce registered-teachers
  (atom {:jr0cket {:language "clojure" :devtools ["emacs" "lighttable" "atom"] :github-pro true}
         :mbutlerw {:language ["JavaScript" "Ruby"] :devtools ["atom"] :github-pro true} }))

;; (deref  registered-teachers)
;; @registered-teachers

(defn teachers
  "Get teachers by Github handles"
  [request]
  (let [teachers (map first @registered-teachers)]
    (str "Registered Teachers are: " (apply str teachers))))

;; (map first @registered-teachers)

(defn current-teachers
  "Get teachers by Github handles"
  [the-teachers]
  (let [teachers (map first the-teachers)]
    (str "Registered Teachers are: " (apply str teachers))))


;; ((teachers :jr0cket) :language)
(get-in teachers [:jr0cket :language])

#_(defn add-teacher
  "Adds a teacher to the current list and returns all teachers"
  [request]
  (let [teacher ,,,,]
   (cons teachers teacher)))

(defn register-teacher
  [request]
  (let [github-name (get-in request [:params :github])
        language-list (get-in request [:params :language])
        devtools-list (get-in request [:params :devtools])
        github-pro (get-in request [:params :github-pro])
        new-teacher {(keyword github-name) {:language language-list :devtools devtools-list :github-pro github-pro}}]
    (swap! registered-teachers conj new-teacher))
  #_(redirect "/teachers"))


#_(let [github-name "blah"
      language-list "rusty"
      devtools-list "emacsy"
      github-pro true
      new-teacher {(keyword github-name) {:language language-list :devtools devtools-list :github-pro github-pro}}]
  (swap! registered-teachers conj new-teacher))

#_(swap! registered-teachers conj  {:fish {:language "clojure" :devtools "emacs" :github-pro true}})
;; (conj registered-teachers {:fish {:language "clojure" :devtools "emacs" :github-pro true}})

;; @registered-teachers

(defn register-teacher-and-redirect [request]
  (register-teacher request)
  (teachers request))

(defroutes app-routes
  (route/resources "/")
  (GET "/" [] (redirect "/register.html"))
  (GET "/teachers" [] teachers)
  (POST "/register-teacher" [] register-teacher-and-redirect)
  (route/not-found "Not Found"))


#_ #(-> %
        register-teacher
        teachers)

#_#(-> %
  register-teacher
    teachers)

(def app
  (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)))


#_(defn allthethings
  ([]
   (allthethings "default arg"))
  ([one-arg]
   (str "you said" one-arg))
  ([one-arg two-args & all-other-args]
   (apply str args)))

;; (allthethings "silly" "billy" "goat")

;; (doc foo)

#_(defn foo
    "This is an awesome function"
    [request]
    (str "get something from the request map"))

;; (def foo (fn [request] (str "get something from request map")))

#_(def teacher ["clojure" "emacs" "yes"])

#_(def teachers [{:language ["clojure"] :devtools ["emacs" "lighttable" "atom"] :github "yes"}
                 {:language "javascript" :devtools "atom" :github "yes"}])



;; (map (get :language) teachers)
(map (fn [teacher-map] (get teacher-map :language)) teachers)
(map #(get % :language) teachers)  ;;map the function over eache element of the

;;; # ()

;; {"language" "javascript" 2 "atom" 3 (do-i-know-github)}

;;(teacher :language)

;; languages
;; dev-tools
;; github

;; list, vector, map, set
;; string
