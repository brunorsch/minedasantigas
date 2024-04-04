package brunorsch.minedasantigas.utils;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = PRIVATE)
@Getter
public class ResultRunnable {
    private Runnable sucesso;
    private Runnable erro;

    public static ResultRunnable create() {
        return new ResultRunnable(() -> {}, () -> {});
    }

    public ResultRunnable onSucesso(Runnable runnable) {
        this.sucesso = runnable;
        return this;
    }

    public ResultRunnable onErro(Runnable runnable) {
        this.erro = runnable;
        return this;
    }
}