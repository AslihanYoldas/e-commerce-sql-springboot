package com.example.e_commerce_database_management.constants;

public  enum CustomerHeaders {
    customer_name, customer_phone_number, customer_email, customer_password, customer_registration_date;
    public static String[] getHeaders() {
        return new String[] {
            customer_name.name(),
            customer_phone_number.name(),
            customer_email.name(),
            customer_password.name(),
            customer_registration_date.name()
        };
    }
}