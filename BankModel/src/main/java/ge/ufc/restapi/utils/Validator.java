package ge.ufc.restapi.utils;

import ge.ufc.restapi.exceptions.ValidatorException;
import ge.ufc.restapi.model.Card;
import ge.ufc.restapi.model.CardHolder;

import java.sql.Date;
import java.util.Calendar;

public class Validator {
  private static final class CardValidator {
    private static final String cardNumberPattern = "^\\d{16}$";
    private static final String ccvPattern = "^\\d{3}$";

    private static void validate(Card card) throws ValidatorException {
      if (!String.valueOf(card.getCardNumber()).matches(cardNumberPattern)) throw new ValidatorException("invalid card number");
      if (!String.valueOf(card.getCCV()).matches(ccvPattern)) throw new ValidatorException("invalid CCV");
      Calendar calendar = Calendar.getInstance();
      Date date = new Date(calendar.getTimeInMillis());
      if (!card.getExpDate().after(date)) throw new ValidatorException("Invalid Expire Date");
    }
  }

  private static final class CardHolderValidator {
    private static final String alphabeticPattern = "[a-zA-Z.]+";

    public static void validate(CardHolder cardHolder) throws ValidatorException {
      if (cardHolder.getId() <= 0) throw new ValidatorException("invalid cardHolder id");
      if (!cardHolder.getFirstName().matches(alphabeticPattern) || !cardHolder.getLastName().matches(alphabeticPattern))
        throw new ValidatorException("invalid cardHolder firstname/lastname");
      if (!cardHolder.getEmail().matches(alphabeticPattern.concat(".").concat(alphabeticPattern).concat("@gmail.com")))
        throw new ValidatorException("invalid cardHolder mail");
    }
  }

  public void validate(CardHolder cardHolder) throws ValidatorException {
    CardHolderValidator.validate(cardHolder);
  }

  public void validate(Card card) throws ValidatorException {
    CardValidator.validate(card);
  }
}
