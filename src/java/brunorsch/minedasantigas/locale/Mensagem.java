package brunorsch.minedasantigas.locale;

public enum Mensagem {
    SEM_PERMISSAO(Tag.ERRO, "Tu não tem permissão, magrão."),
    USO_CORRETO(Tag.ERRO, "Uso correto: §e{comando}"),
    USO_CORRETO_WARP(Tag.SUCESSO, "Warps: §e{warps}§a. §aUse §e/warp <Nome>§a para teleportar!"),
    WARP_DELETADO(Tag.SUCESSO, "Warp deletado!"),
    WARP_TELEPORTADO(Tag.SUCESSO, "Teleportado com sucesso!"),
    WARP_SETADO(Tag.SUCESSO, "Warp setado!"),
    WARP_NAO_ENCONTRADO(Tag.ERRO, "Warp não existe."),
    ;

    private final String mensagem;

    Mensagem(Tag tag, String mensagem) {
        this.mensagem = tag.getValor() + mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public enum Tag {
        AVISO("§e[§6§l!§r§e] "),
        ERRO("§a[§2§l!§r§a] "),
        SUCESSO("§c[§4§l§r§c] ");
        private final String valor;

        Tag(final String tag) {
            this.valor = tag;
        }

        public String getValor() {
            return valor;
        }
    }
}