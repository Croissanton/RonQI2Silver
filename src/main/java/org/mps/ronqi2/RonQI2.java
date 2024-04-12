//Autores: Cristian Ruiz Martín y Mikolaj Zabski

package org.mps.ronqi2;

import org.mps.dispositivo.Dispositivo;

public abstract class RonQI2 {
    protected Dispositivo disp;

    /* 
     * Inicializa el sistema ronQI2 configurando los dos sensores del dispositivo conectado.
    */
    public boolean inicializar(){
        boolean result = false;
        if (disp.conectarSensorPresion()){
            boolean confPresion = disp.configurarSensorPresion();
            if (disp.conectarSensorSonido()){
                result = disp.configurarSensorSonido() && confPresion;
            }
        }
        return result;   
    }

    /* 
     * Lee y almacena las lecturas de presion y sonido del dispositivo.
    */
    public abstract void obtenerNuevaLectura();

    public void anyadirDispositivo(Dispositivo d){
        disp = d;
    }

    /* 
     * Reconecta el dispositivo cuando esta desconectado.
    */
    public boolean reconectar(){
        boolean result = false;
        if (!estaConectado()){ // ERROR 1: DEBERÍA LLAMAR AL MÉTODO estaConectado(), DECLARADO EN ESTA MISMA CLASE, EN VEZ DE AL MÉTODO DEL MISMO NOMBRE DE LA CLASE Dispositivo.
            result = disp.conectarSensorPresion() && disp.conectarSensorSonido();
        }
        return result;
    }

    /* 
     * Evalua la apnea del suenyo con las medidas recogidas.
    */
    public abstract boolean evaluarApneaSuenyo();
    
    /* 
     * Lee y almacena las lecturas de presion y sonido del dispositivo.
    */
    public boolean estaConectado(){
        return disp.estaConectado();
    }    
}
