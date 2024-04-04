package org.mps.ronqi2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.dispositivo.Dispositivo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class ronQI2Silvertest {

    
    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */

    @Test
    @DisplayName("Inicializar cuando se pueden conectar ambos sensores devuelve true")
    public void InicializarReturnsTrueCuandoSePuedenConectarYConfigurarAmbosSensores(){
        Dispositivo disp = mock(Dispositivo.class);
        when (disp.conectarSensorPresion()).thenReturn(true);
        when (disp.configurarSensorPresion()).thenReturn(true);
        when (disp.conectarSensorSonido()).thenReturn(true);
        when (disp.configurarSensorSonido()).thenReturn(true);
        RonQI2Silver ronQI2 = new RonQI2Silver();
        ronQI2.anyadirDispositivo(disp);
        assertTrue(ronQI2.inicializar());
    }

    @Test
    @DisplayName("Inicializar cuando no se puede conectar alguno de los sensores devuelve false")
    public void InicializarReturnsFalseCuandoNoSePuedeConectarAlgunoDeLosSensores(){
        Dispositivo disp = mock(Dispositivo.class);
        when (disp.conectarSensorPresion()).thenReturn(false);
        when (disp.conectarSensorSonido()).thenReturn(true);
        RonQI2Silver ronQI2 = new RonQI2Silver();
        ronQI2.anyadirDispositivo(disp);
        assertTrue(!ronQI2.inicializar());
    }

    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */


    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     * /
     
     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */
}
