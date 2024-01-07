package com.android.ellocker.helper;

import com.google.android.material.textfield.TextInputEditText;

public interface Validotor {

    default void validateUserPhone(String userPhone, TextInputEditText tietnomorponsel) {
        if (userPhone.length() <= 0) {
            tietnomorponsel.setError("Hanya angka yang diperbolehkan");
        } else if (userPhone.length() < 10 || userPhone.length() > 13) {
            tietnomorponsel.setError("Panjang nomor harus antara 10 dan 13 digit");
        } else if (!userPhone.matches("\\d+") ) {
            tietnomorponsel.setError("Nomor Ponsel Harus di Isi");
        } else {
            tietnomorponsel.setError(null);
        }
    }

    default void validateEmail(String email, TextInputEditText tietemail) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tietemail.setError("Invalid email format");
        } else {
            tietemail.setError(null);
        }
    }


    default void validatePassword(String password, TextInputEditText tietpassword) {
        if (password.length() < 8) {
            tietpassword.setError("Password harus memiliki minimal panjang 8 karakter");
        } else if (!password.matches(".*[A-Z].*")) {
            tietpassword.setError("Password harus mengandung setidaknya satu huruf besar");
        } else if (!password.matches(".*[a-z].*")) {
            tietpassword.setError("Password harus mengandung setidaknya satu huruf kecil");
        } else if (!password.matches(".*\\d.*")) {
            tietpassword.setError("Password harus mengandung setidaknya satu angka");
        } else {
            tietpassword.setError(null);
        }
    }
}
