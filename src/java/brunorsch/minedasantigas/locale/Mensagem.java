package brunorsch.minedasantigas.locale;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum Mensagem {
    MUNDO_NAO_ENCONTRADO(Tag.ERRO, "Mundo informado não encontrado."),
    PLAYER_NAO_ENCONTRADO(Tag.ERRO, "Jogador informado não encontrado."),
    SEM_PERMISSAO(Tag.ERRO, "Tu não tem permissão, magrão."),
    TELEPORTADO_COM_SUCESSO(Tag.SUCESSO, "Teleportado com sucesso!"),
    USO_CORRETO(Tag.ERRO, "Uso correto: §e{comando}"),
    USO_CORRETO_WARP(Tag.SUCESSO, "Warps: §e{warps}§a. §aUse §e/warp <Nome>§a para teleportar!"),
    WARP_DELETADO(Tag.SUCESSO, "Warp deletado!"),
    WARP_SETADO(Tag.SUCESSO, "Warp setado!"),
    WARP_NAO_ENCONTRADO(Tag.ERRO, "Warp não existe."),
    XYZ_DEVE_SER_NUMERO(Tag.ERRO, "X, Y e Z devem ser números"),
    SPAWN_NAO_SETADO(Tag.ERRO, "O spawn do servidor não foi setado!"),
    SPAWN_SETADO(Tag.SUCESSO, "Spawn setado!");

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