package com.arautos.confessioae.security

import android.app.Activity
import android.view.WindowManager
import com.arautos.confessioae.BuildConfig

/**
 * Gerencia as políticas de segurança globais do aplicativo.
 */
object SecurityManager {

    /**
     * Aplica as políticas de segurança à Activity fornecida.
     * 
     * Em modo RELEASE: Ativa FLAG_SECURE para impedir screenshots e gravações.
     * Em modo DEBUG: Permite screenshots para facilitar o desenvolvimento.
     */
    fun applySecurityPolicies(activity: Activity) {
        if (!BuildConfig.DEBUG) {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }

    // Espaço preparado para futuras funcionalidades de segurança:
    // fun checkBiometricAuth() { ... }
    // fun isDeviceRooted(): Boolean { ... }
    // fun clearSensitiveDataFromRAM() { ... }
}
