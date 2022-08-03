package com.mydomomain.silverpay.Routes.V1;

public class Routes {


    private static final String base_url = "V1/api/site/panel";



    //region auth routes
    public static class Auth {

        public static final String login = base_url+"/auth/login";

        public static final String register = base_url+"/auth/register";

    }
    //endregion

    //region user routes
    public static class User {


        public static final String users = base_url+"/users";

        public static final String user = base_url + "/users/{id}";

        public static final String insert =  base_url + "/users";

        public static final String update =  base_url + "/users/{id}";

        public static final String reset_password =  base_url + "/users/resetPassword/{id}";

        public static final String delete =  base_url + "/users/{id}";
    }
    //endregion

    //region photo routes
    public static class Photo {

        public static final String photos = base_url+"/users/{user_id}/photos";

        public static final String photo = base_url+"/users/{user_id}/photos/{id}";

        public static final String upload = base_url+"/users/{user_id}/photos";

       // public static final String update = base_url + "/photos/{id}";

        public static final String delete = base_url + "/users/{user_id}/photos/{id}";
    }
    //endregion

    //region Admin_user routes
    public static class AdminUser {


        public static final String users = base_url+"/adminusers";

        public static final String editRoles=base_url+"/adminusers/editroles/{username}";

    }
    //endregion

    //region photo notification
    public static class Notification {

        public static final String updateNotify = base_url+"users/{user_id}/notifications";

        public static final String getUserNotify = base_url+"users/{user_id}/notifications/{notify_id}";




    }
    //endregion

    //region bankCard routes
    public static class BankCard {

        public static final String bankCards = base_url+"/users/{user_id}/bankCards";

        public static final String bankCard = base_url+"/users/{user_id}/bankCards/{id}";

        public static final String insert = base_url+"/users/{user_id}/bankCards";

        public static final String update = base_url + "/bankCards/{id}";

        public static final String delete = base_url + "/bankCards/{id}";
    }
    //endregion

    //region document routes
    public static class Document {

        public static final String get_documents = base_url+"/users/{user_id}/documents";

        public static final String get_document = base_url+"/users/{user_id}/documents/{id}";

        public static final String insert_document = base_url+"/users/{user_id}/documents";

    }
    //endregion

    //region wallet routes
    public static class Wallet {

        public static final String get_wallets = base_url+"/users/{user_id}/wallet";

        public static final String get_wallet = base_url+"/users/{user_id}/wallets/{id}";

        public static final String insert_wallet = base_url+"/users/{user_id}/wallets";

    }
    //endregion


}
