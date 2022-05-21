package sptech.bentscadastro.util.formatt;

import javax.swing.text.MaskFormatter;

public class FormattPhone {
    public static String formattPhone(String phone) throws java.text.ParseException {
        phone = phone.replaceAll("[-+.^:() ,]","");
        String maskPhone = "(AA) AAAAA-AAAA";
        if(phone.startsWith("0")) phone = phone.substring(1);
        MaskFormatter mf = new MaskFormatter(maskPhone);
        mf.setValueContainsLiteralCharacters(false);
        return mf.valueToString(phone);
    }
}
