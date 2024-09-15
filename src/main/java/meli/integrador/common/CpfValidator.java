package meli.integrador.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import meli.integrador.annotations.Cpf;
import meli.integrador.exception.InvalidCPFException;

public class CpfValidator implements ConstraintValidator<Cpf, String> {

    @Override
    public void initialize(Cpf constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}") ||
            cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") ||
                cpf.equals("33333333333") || cpf.equals("44444444444") ||
                cpf.equals("55555555555") || cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999")){
            return false;
        }

        try {
            int sum = 0;
            int weight = 10;

            for (int i = 0; i < 9; i++) {
                sum += (cpf.charAt(i) - '0') * weight--;
            }

            int mod = sum % 11;
            int firstDigit = mod < 2 ? 0 : 11 - mod;

            if (firstDigit != cpf.charAt(9) - '0') {
                return false;
            }

            sum = 0;
            weight = 11;

            for (int i = 0; i < 10; i++) {
                sum += (cpf.charAt(i) - '0') * weight--;
            }

            mod = sum % 11;
            int secondDigit = mod < 2 ? 0 : 11 - mod;

            return secondDigit == cpf.charAt(10) - '0';
        } catch (Exception e) {
            return false;
        }
    }
}