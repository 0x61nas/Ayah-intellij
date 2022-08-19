package com.anas.intellij.plugins.ayah.settings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/19/22
 */
@Getter
@Setter
@NoArgsConstructor
public class BasmalhOnStart {
    private boolean isActive;
    private boolean isNotificationActive;
    private boolean isSoundActive;
    private String playerId;
}
