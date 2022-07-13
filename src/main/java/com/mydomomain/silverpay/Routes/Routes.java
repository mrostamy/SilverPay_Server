package com.mydomomain.silverpay.Routes;

public class Routes {

    private static final String base_url = "api/site/panel";

    //region auth
    public static class Auth {
        public static final String url = base_url + "/auth";

        public static final String login = url+"/login";
        public static final String register = url+"/register";

    }
    //endregion


    //region user
    public static class User {
        public static final String url = base_url + "/users";

        public static final String users = url;
        public static final String user = url + "/{id}";
        public static final String update = url + "{id}";
        public static final String delete = url + "/{id}";
    }
    //endregion

    //region photo
    public static class Photo {
        public static final String url = base_url + "/users/{userId}/photos";

        public static final String photos = url;
        public static final String photo = url + "/{id}";
        public static final String update = url + "/{id}";
        public static final String delete = url + "/{id}";
    }
    //endregion

}
