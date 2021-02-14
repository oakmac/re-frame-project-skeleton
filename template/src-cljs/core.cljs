(ns {{PROJECT_NAME}}.core
  (:require
    [goog.dom :as gdom]
    [goog.functions :as gfunctions]
    [oops.core :refer [ocall]]
    [{{PROJECT_NAME}}.routing :as routing]
    [{{PROJECT_NAME}}.views.root :refer [AppRoot]]
    [re-frame.core :as rf]
    [reagent.dom :as reagent-dom]
    [taoensso.timbre :as timbre]))

(def app-container-el (gdom/getElement "appContainer"))

(defn on-refresh
  "Forces a Reagent re-render of all components.
   NOTE: this function is called after every shadow-cljs hot module reload"
  []
  (timbre/info "Re-rendering now")
  (rf/clear-subscription-cache!)
  (reagent-dom/force-update-all))

(def start-rendering!
  (gfunctions/once
    (fn []
      (timbre/info "Begin rendering")
      (reagent-dom/render [(var AppRoot)] app-container-el))))

(def init!
  "Global application init.
  Note: this function may only be called once."
  (gfunctions/once
    (fn []
      (timbre/info "Initializing {{PROJECT_NAME}} UI")
      (if-not app-container-el
        (timbre/fatal "<div id=appContainer> element not found")
        (do
          (timbre/info "Initializing app-db")
          (rf/dispatch-sync [:{{PROJECT_NAME}}/init-app-db])
          (start-rendering!)
          (routing/init!))))))

(ocall js/window "addEventListener" "load" init!)
