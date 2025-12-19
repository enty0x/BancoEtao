-----Sobre el proyecto y las funcionalidades:
La GUI esta levantada desde la vista de un administrador, el cual puede acceder a las operaciones de la base de datos y actualizarla
con datos nuevos o eliminar los existentes (borrando el archivo de persistencia). Este puede manejar metodos de distintos objetos,
como las transferencias, depositos, hacer que un cliente/usuario tenga una cuenta RUT/CORRIENTE/AHORRO y sus respectiva clave unica,
agregar ejecutivos y cajeros nuevos al sistema, etc (Todo lo que se muestra en el GUI).

-----Sobre las utilidades del GUI:
    -|Crear ejecutivo: El sistema se encarga de crear un ejecutivo con nombre "Ejecutivo N", con id N y pertenecientes a una sucursal unica.

    -|Crear cajero: El sistema se encarga de crear un cajero con nombre "Cajero N", con id N y montoInterior de 500000 (aunque no se especifica).

    -|Crear cliente: El sistema crea un cliente con los datos de los campos que se encuentran en la primera pestaña, arriba a la derecha.
        !!IMPORTANTE: El cliente no puede ya existiren el sistema para crearse(haber uno con el mismo id/cedula).
        El cliente puede crearse con una edad desde 0 hasta N años. Pueden repetirse nombres pero no IDs en el sistema.

    -|Crear cuenta: Una vez seleccionado el cliente y el ejecutivo con el que se realizaran las operaciones, el ejecutivo se
    encarga de verificar que el cliente no tenga esa cuenta previamente y que cumpla la edad minima (>14), luego de eso integra la solicitud
    del cliente al sistema, dandole una id a la solicitud para trabajarla desde sistema, el cual se encarga aprobar la solicitud y crear
    el contrato para la cuenta del cliente C, guardando esta en las listas de cuentas correspondientes, luego, el ejecutivo se encarga
    de firmar el contrato, el cliente C lo recibe, lo firma
    y luego la cuenta que solicito C sera la cuenta que el ejecutivo debe buscar respecto a C.

-----Forma de uso (compilacion):
Para la compilacion del proyecto se debe ejecutar la clase main en la carpeta src, o puede usarse tambien la clase BancoGUI,
nosotros realizamos la compilacion con IDE (En nuestro caso IntelliJ IDEA), sin complicarnos con la terminal y ejecutando la clase main.