import java.util.*;

// --------------------------------------------
// PATRÓN CREACIONAL - FACTORY METHOD
// --------------------------------------------

abstract class CitaMedica {
    protected String paciente;
    protected String especialidad;

    public CitaMedica(String paciente, String especialidad) {
        this.paciente = paciente;
        this.especialidad = especialidad;
    }

    public abstract void confirmarCita();
}

class CitaOdontologica extends CitaMedica {
    public CitaOdontologica(String paciente) {
        super(paciente, "Odontología");
    }

    public void confirmarCita() {
        System.out.println("Confirmando cita odontológica para: " + paciente);
    }
}

class CitaPsicologica extends CitaMedica {
    public CitaPsicologica(String paciente) {
        super(paciente, "Psicología");
    }

    public void confirmarCita() {
        System.out.println("Confirmando cita psicológica para: " + paciente);
    }
}

class CitaFactory {
    public static CitaMedica crearCita(String tipo, String paciente) {
        if (tipo.equalsIgnoreCase("odontologia")) {
            return new CitaOdontologica(paciente);
        } else if (tipo.equalsIgnoreCase("psicologia")) {
            return new CitaPsicologica(paciente);
        }
        throw new IllegalArgumentException("Tipo de cita no válido");
    }
}

// --------------------------------------------
// PATRÓN DE COMPORTAMIENTO - OBSERVER
// --------------------------------------------

interface Observador {
    void actualizar(String mensaje);
}

class Paciente implements Observador {
    private String nombre;

    public Paciente(String nombre) {
        this.nombre = nombre;
    }

    public void actualizar(String mensaje) {
        System.out.println(nombre + " recibió: " + mensaje);
    }

    public String getNombre() {
        return nombre;
    }
}

// --------------------------------------------
// PATRÓN ESTRUCTURAL - ADAPTER
// --------------------------------------------

interface Notificador {
    void enviar(String mensaje);
}

class NotificadorEmail implements Notificador {
    public void enviar(String mensaje) {
        System.out.println("Enviando Email: " + mensaje);
    }
}

class NotificadorSMS implements Notificador {
    public void enviar(String mensaje) {
        System.out.println("Enviando SMS: " + mensaje);
    }
}

// --------------------------------------------
// GESTOR DE CITAS
// --------------------------------------------

class GestorCitas {
    private List<Observador> observadores = new ArrayList<>();
    private Notificador notificador;

    public GestorCitas(Notificador notificador) {
        this.notificador = notificador;
    }

    public void registrarObservador(Observador o) {
        observadores.add(o);
    }

    public void confirmarCita(CitaMedica cita) {
        cita.confirmarCita();
        String mensaje = "Cita confirmada para: " + cita.paciente + " - " + cita.especialidad;
        notificador.enviar(mensaje);
        notificarObservadores(mensaje);
    }

    private void notificarObservadores(String mensaje) {
        for (Observador o : observadores) {
            o.actualizar(mensaje);
        }
    }
}

// --------------------------------------------
// MAIN PRINCIPAL
// --------------------------------------------

public class SistemaReservas {
    public static void main(String[] args) {
        Paciente paciente1 = new Paciente("Lucía Pérez");
        Paciente paciente2 = new Paciente("Carlos Díaz");

        CitaMedica cita1 = CitaFactory.crearCita("odontologia", paciente1.getNombre());

        Notificador notificador = new NotificadorSMS();

        GestorCitas gestor = new GestorCitas(notificador);

        gestor.registrarObservador(paciente1);
        gestor.registrarObservador(paciente2);

        gestor.confirmarCita(cita1);
    }
}

