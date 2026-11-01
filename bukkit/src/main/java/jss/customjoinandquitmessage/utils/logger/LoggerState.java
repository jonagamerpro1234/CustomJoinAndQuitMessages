
package jss.customjoinandquitmessage.utils.logger;

import java.time.LocalDateTime;

public class LoggerState {

    private boolean shutdownCompleted;
    private LocalDateTime lastUpdated;

    public LoggerState(boolean shutdownCompleted, LocalDateTime lastUpdated) {
        this.shutdownCompleted = shutdownCompleted;
        this.lastUpdated = lastUpdated;
    }

    public boolean isShutdownCompleted() {
        return shutdownCompleted;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void update(boolean shutdownCompleted) {
        this.shutdownCompleted = shutdownCompleted;
        this.lastUpdated = LocalDateTime.now();
    }
}
