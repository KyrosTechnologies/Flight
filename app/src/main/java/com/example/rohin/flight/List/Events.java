package com.example.rohin.flight.List;

/**
 * Created by Rohin on 27-04-2017.
 */

public class Events {
    public Events (){

    }

    public static class FragmentActivityMessage {
        private String message;
        public String location;


        public FragmentActivityMessage(){

        }


        public FragmentActivityMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

    public static class ActivityFragmentMessage {

        private String message;

        public ActivityFragmentMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
    public static class LocationMessage{
        private String location;
        public LocationMessage(String location){
            this.location=location;
        }
        public String getLocation(){
            return location;
        }
    }
}
