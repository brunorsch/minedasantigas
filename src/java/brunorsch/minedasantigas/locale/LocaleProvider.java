package brunorsch.minedasantigas.locale;

import static brunorsch.minedasantigas.utils.CollectionUtils.mapOf;
import static brunorsch.minedasantigas.utils.CollectionUtils.pair;

import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

public class LocaleProvider {
    public static String msg(Mensagem mensagem, Map<String, String> replaces) {
        return new StrSubstitutor(replaces, "{", "}", '\\')
            .replace(mensagem.getMensagem());
    }

    public static String msg(Mensagem mensagem) {
        return mensagem.getMensagem();
    }

    public static String msg(Mensagem mensagem, Map.Entry<String, String> entry) {
        return msg(mensagem, mapOf(entry));
    }

    public static String usoCorreto(String usoCorreto) {
        return msg(Mensagem.USO_CORRETO, pair("comando", usoCorreto));
    }
}