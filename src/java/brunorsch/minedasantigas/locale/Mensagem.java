package brunorsch.minedasantigas.locale;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum Mensagem {
    ERRO_SQL(Tag.ERRO, "Erro com o banco de dados. Contate o Preguiça imediatamente!"),
    HOME_NAO_ENCONTRADA(Tag.ERRO, "Home não encontrada!"),
    HOME_SETADA(Tag.SUCESSO, "Home §e{home} §asetada com sucesso!"),
    LOJA_BEM_VINDO(Tag.SUCESSO, "Bem vindo a loja §e{loja}§a!"),
    LOJA_CRIADA(Tag.SUCESSO, "Loja criada!"),
    LOJA_JA_EXISTE(Tag.ERRO, "Uma loja com esse nome já existe!"),
    LOJA_NAO_ENCONTRADA(Tag.ERRO, "Loja não encontrada!"),
    MUNDO_NAO_ENCONTRADO(Tag.ERRO, "Mundo informado não encontrado."),
    PLAYER_NAO_ENCONTRADO(Tag.ERRO, "Jogador informado não encontrado."),
    PWARP_DICA(Tag.AVISO, "Se usar só §d/pwarp <Nome do warp>§e, o player que vai ser considerado é tu mesmo"),
    PWARP_DICA2(Tag.AVISO, "Ex: §d/pwarp <Nome>§e == §d/pwarp {player} <Nome>"),
    PWARP_DICA3(Tag.AVISO, "Para listar warps, use /pwarp [Player] list"),
    PWARP_LISTA(Tag.SUCESSO, "Warps de §2{player}§a: §e{warps}§a."),
    SEM_PERMISSAO(Tag.ERRO, "Tu não tem permissão, magrão."),
    TELEPORTADO_COM_SUCESSO(Tag.SUCESSO, "Teleportado com sucesso!"),
    TELEPORTADO_PARA_CAMA(Tag.AVISO, "Teleportado para sua cama!"),
    USO_CORRETO(Tag.ERRO, "Uso correto: §e{comando}"),
    USO_CORRETO_WARP(Tag.SUCESSO, "Warps: §e{warps}§a. §aUse §e/warp <Nome>§a para teleportar!"),
    WARP_DELETADO(Tag.SUCESSO, "Warp deletado!"),
    WARP_SETADO(Tag.SUCESSO, "Warp setado!"),
    WARP_NAO_ENCONTRADO(Tag.ERRO, "Warp não existe."),
    XYZ_DEVE_SER_NUMERO(Tag.ERRO, "X, Y e Z devem ser números"),
    SPAWN_NAO_SETADO(Tag.ERRO, "O spawn do servidor não foi setado!"),
    SPAWN_SETADO(Tag.SUCESSO, "Spawn setado!"),
    ONLINE(Tag.AVISO, "Tempo online no server: &d{horas} horas &ee &d{minutos} minutos&e!");

    private final String mensagem;

    Mensagem(Tag tag, String mensagem) {
        this.mensagem = tag.getValor() + mensagem;
    }

    @Getter
    @AllArgsConstructor
    public enum Tag {
        AVISO("§e[§6§l!§r§e] "),
        ERRO("§c[§4§l!§r§c] "),
        SUCESSO("§a[§2§l!§r§a] ");
        private final String valor;
    }
}