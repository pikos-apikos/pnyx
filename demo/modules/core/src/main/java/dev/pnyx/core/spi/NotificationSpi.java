package dev.pnyx.core.spi;

/**
 * Driven port for notifying participants about civic-loop changes.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PROTOCOL.md}, the system notifies relevant parties when
 * proposals transition between states, require clarification, or reach decision points.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public interface NotificationSpi {

    void notify(String event, String message);
}
