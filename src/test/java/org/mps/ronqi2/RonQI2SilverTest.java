package org.mps.ronqi2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mps.dispositivo.Dispositivo;
import org.mps.dispositivo.DispositivoSilver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class RonQI2SilverTest {

    @Mock
    Dispositivo mockDispositivo;
    RonQI2Silver ronQI2;

    @BeforeEach
    public void setUp(){
        mockDispositivo = mock(Dispositivo.class);

        ronQI2 = new RonQI2Silver();
        ronQI2.anyadirDispositivo(mockDispositivo);
    }

    
    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */

    @Test
    @DisplayName("Inicializar cuando se pueden conectar ambos sensores devuelve true")
    public void Inicializar_CuandoSePuedenConectarYConfigurarAmbosSensores_ReturnsTrue(){
        when (mockDispositivo.conectarSensorPresion()).thenReturn(true);
        when (mockDispositivo.configurarSensorPresion()).thenReturn(true);
        when (mockDispositivo.conectarSensorSonido()).thenReturn(true);
        when (mockDispositivo.configurarSensorSonido()).thenReturn(true);

        assertTrue(ronQI2.inicializar());
    }

    @Test
    @DisplayName("Inicializar cuando no se puede conectar el sensor de presion devuelve false")
    public void Inicializar_CuandoNoSePuedeConectarSensorPresion_DevuelveFalse(){
        when (mockDispositivo.conectarSensorPresion()).thenReturn(false);
        when (mockDispositivo.configurarSensorPresion()).thenReturn(true);
        when (mockDispositivo.conectarSensorSonido()).thenReturn(true);
        when (mockDispositivo.configurarSensorSonido()).thenReturn(true);

        assertFalse(ronQI2.inicializar());
    }

    @Test
    @DisplayName("Inicializar cuando no se puede conectar el sensor de sonido devuelve false")
    public void Inicializar_CuandoNoSePuedeConectarSensorSonido_DevuelveFalse(){
        when (mockDispositivo.conectarSensorPresion()).thenReturn(true);
        when (mockDispositivo.conectarSensorSonido()).thenReturn(false);
        when (mockDispositivo.configurarSensorPresion()).thenReturn(true);
        when (mockDispositivo.configurarSensorSonido()).thenReturn(true);

        assertFalse(ronQI2.inicializar());
    }

    @Test
    @DisplayName("Inicializar cuando no se puede configurar el sensor de presion devuelve false")
    public void Inicializar_CuandoNoSePuedeConfigurarSensorPresion_DevuelveFalse(){
        when (mockDispositivo.conectarSensorPresion()).thenReturn(true);
        when (mockDispositivo.configurarSensorPresion()).thenReturn(false);
        when (mockDispositivo.conectarSensorSonido()).thenReturn(true);
        when (mockDispositivo.configurarSensorSonido()).thenReturn(true);

        assertFalse(ronQI2.inicializar());
    }

    @Test
    @DisplayName("Inicializar cuando no se puede configurar el sensor de sonido devuelve false")
    public void Inicializar_CuandoNoSePuedeConfigurarSensorSonido_DevuelveFalse(){
        when (mockDispositivo.conectarSensorPresion()).thenReturn(true);
        when (mockDispositivo.configurarSensorPresion()).thenReturn(true);
        when (mockDispositivo.conectarSensorSonido()).thenReturn(true);
        when (mockDispositivo.configurarSensorSonido()).thenReturn(false);

        assertFalse(ronQI2.inicializar());
    }

    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */
    @Test
    @DisplayName("Inicializar cuando se inicializa correctamente llama a configurarSensorPresion y configurarSensorSonido una vez")
    public void Inicializar_CuandoSePuedenConectarYConfigurarAmbosSensores_LlamaALosMetodosDeConfigurarUnaVez(){
        when (mockDispositivo.conectarSensorPresion()).thenReturn(true);
        when (mockDispositivo.configurarSensorPresion()).thenReturn(true);
        when (mockDispositivo.conectarSensorSonido()).thenReturn(true);
        when (mockDispositivo.configurarSensorSonido()).thenReturn(true);

        ronQI2.inicializar();

        verify(mockDispositivo, times(1)).configurarSensorPresion();
        verify(mockDispositivo, times(1)).configurarSensorSonido();
    }


    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */
    @Test
    @DisplayName("Reconectar cuando el dispositivo no está conectado conecta ambos sensores y devuelve true")
    public void Reconectar_CuandoElDispositivoNoEstaConectado_ConectaSensoresDevuelveTrue(){
        when (mockDispositivo.estaConectado()).thenReturn(false);
        when (mockDispositivo.conectarSensorPresion()).thenReturn(true);
        when (mockDispositivo.conectarSensorSonido()).thenReturn(true);

        assertTrue(ronQI2.reconectar());
        verify(mockDispositivo, times(1)).conectarSensorPresion();
        verify(mockDispositivo, times(1)).conectarSensorSonido();
    }

    @Test
    @DisplayName("Reconectar cuando el dispositivo esta conectado no conecta ningun sensor y devuelve false")
    public void Reconectar_CuandoElDispositivoEstaConectadoNoConectaSensores_DevuelveFalse(){
        when (mockDispositivo.estaConectado()).thenReturn(true);

        assertFalse(ronQI2.reconectar());
        verify(mockDispositivo, never()).conectarSensorPresion();
        verify(mockDispositivo, never()).conectarSensorSonido();
    }

    @Test
    @DisplayName("Reconectar cuando el dispositivo no está conectado y no se puede conectar el sensor de presion devuelve false")
    public void Reconectar_CuandoElDispositivoNoEstaConectadoYNoSePuedeConectarSensorPresion_DevuelveFalse(){
        when (mockDispositivo.estaConectado()).thenReturn(false);
        when (mockDispositivo.conectarSensorPresion()).thenReturn(false);
        when (mockDispositivo.conectarSensorSonido()).thenReturn(true);

        assertFalse(ronQI2.reconectar());
        verify(mockDispositivo, times(1)).conectarSensorPresion();
        verify(mockDispositivo, never()).conectarSensorSonido();
    }

    @Test
    @DisplayName("Reconectar cuando el dispositivo no está conectado y no se puede conectar el sensor de sonido devuelve false")
    public void Reconectar_CuandoElDispositivoNoEstaConectadoYNoSePuedeConectarSensorSonido_DevuelveFalse(){
        when (mockDispositivo.estaConectado()).thenReturn(false);
        when (mockDispositivo.conectarSensorPresion()).thenReturn(true);
        when (mockDispositivo.conectarSensorSonido()).thenReturn(false);

        assertFalse(ronQI2.reconectar());
        verify(mockDispositivo, times(1)).conectarSensorPresion();
        verify(mockDispositivo, times(1)).conectarSensorSonido();
    }
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     * /

     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */

    @ParameterizedTest
    @DisplayName("EvaluarApneaSuenyo funciona correctamente con distintos números de lecturas")
    @ValueSource(ints = {4, 5, 10})
    public void EvaluarApneaSuenyo_ConDistintosNumerosDeLecturas_ReturnsTrue(int numLecturas){
        ronQI2 = new RonQI2Silver();
        ronQI2.anyadirDispositivo(mockDispositivo);

        for (int i = 0; i < numLecturas; i++) {
            ronQI2.obtenerNuevaLectura();
        }

        assertFalse(ronQI2.evaluarApneaSuenyo());
    }

    @Test
    @DisplayName("EvaluarApneaSuenyo devuelve true si la media de las lecturas de presion y sonido es mayor que thresholdP y thresholdS")
    public void EvaluarApneaSuenyo_MediaMayorQueThresholdPYS_ReturnsTrue(){
        ronQI2 = new RonQI2Silver();
        ronQI2.anyadirDispositivo(mockDispositivo);

        when(mockDispositivo.leerSensorPresion()).thenReturn(30.0f);
        when(mockDispositivo.leerSensorSonido()).thenReturn(40.0f);

        ronQI2.obtenerNuevaLectura();

        assertTrue(ronQI2.evaluarApneaSuenyo());
    }
    @ParameterizedTest
    @DisplayName("EvaluarApneaSuenyo devuelve false si la media de las lecturas de presion es menor que thresholdP o la media de las lecturas de sonido es menor que thresholdS")
    @CsvSource({"10, 40", "30, 20", "10, 20"})
    public void EvaluarApneaSuenyo_MediaPorDebajoDeThreshold_ReturnsFalse(float presion, float sonido){
        ronQI2 = new RonQI2Silver();
        ronQI2.anyadirDispositivo(mockDispositivo);

        when(mockDispositivo.leerSensorPresion()).thenReturn(presion);
        when(mockDispositivo.leerSensorSonido()).thenReturn(sonido);

        ronQI2.obtenerNuevaLectura();

        assertFalse(ronQI2.evaluarApneaSuenyo());

    }
}
