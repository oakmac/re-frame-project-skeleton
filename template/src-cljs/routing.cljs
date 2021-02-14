(ns {{PROJECT_NAME}}.routing
  (:require
   [bide.core :as bide]
   [goog.functions :as gfunctions]
   [oops.core :refer [ocall oget oset!]]
   [re-frame.core :as rf]
   [taoensso.timbre :as timbre]))

(def default-route "/home")

(def router
  (bide/router
    [["/home" "HOME_ROUTE"]
     ["/page1" "PAGE1_ROUTE"]
     ["/page2" "PAGE2_ROUTE"]
     ["/page3" "PAGE3_ROUTE"]]))

(defn- on-hash-change
  "This function is called on every hash change."
  [route-id _params query-args]
  (case route-id
    "HOME_ROUTE" (rf/dispatch [:{{PROJECT_NAME}}.views.home-page/init])
    "PAGE1_ROUTE" (rf/dispatch [:{{PROJECT_NAME}}.views.page1/init])
    "PAGE2_ROUTE" (rf/dispatch [:{{PROJECT_NAME}}.views.page2/init])
    "PAGE3_ROUTE" (rf/dispatch [:{{PROJECT_NAME}}.views.page3/init])
    (do
      (timbre/warn "Unrecognized route:" route-id)
      (oset! js/window "location.hash" default-route))))

(def init!
  "Initialize routing. This function may only be called once."
  (gfunctions/once
    (fn []
      (timbre/info "Initializing routing")
      (bide/start! router {:default :unknown
                           :on-navigate on-hash-change}))))
