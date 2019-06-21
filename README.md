# VentaAutomoviles
Se requiere diseñar un sistema  para una empresa de venta de automóviles, esta empresa fabrica diferentes tipos de autos, y entre ellos también los automóviles híbridos y eléctricos.  Despues de la fabricación se movilizan hacia diferentes sucursales dentro y fuera del país. Cada automóvil tiene N cantidad de características, entre ellas, precio, año, tipo de combustible, color, diferentes tipos de extras, puertas, cantidad de pasajeros , precio por extras, etc.  Es neceario poder mostrar diferentes fotos de cada uno de los automóviles.

Cada sucursal tiene su propio inventario de automóviles,  puede también recibir automóviles por ceonsignación, donde se maneja una comisión por venta

Para el funcionamiento de la aplicación se requieren varios niveles de usuarios, el usuario de consulta, el usuario que factura y el usuario administrador.

Hay una parte que es de la fábrica para poder llevar control de los empleados, funcionamiento, pedidos y despachos.

Un usuario administrador puede consultar, actualizar y asignar precios, ingresar nuevos automóviles y cambiar las características, consultar productos y existencias en todos los establecimientos, consultar ventas x sucursal  x tipo de automóvil  x país y/o por fechas. Ventas  por tipo de pago x sucursal y por fechas.  Usualmente también se desea poder ver cuáles autos son los mas vendidos y cuáles son los que no tienen salida, por sucursal o a nivel global.

Otra parte importante a tomar en cuenta, es que la sucursal puede otorgar crédito al comprador, por lo que cuando alguien desea que se les financie un automóvil se debe entonces cobrar un 20% de prima y luego llevar control de los diferentes pagos que debe realizar a una tasa de interes que puede ser variable.

Un usuario facturador es el encargado de realizar la factura a cada cliente.  Un cliente recibe un 10% de descuento en la compra del automóvil  si es un cliente que ha realizado mas de 3 compras de automóviles en los ultimos 5 años, el descuento puede ser variable.

Deben tomar en cuenta que si cada pago que se realiza es con tarjeta de crédito, se le retiene al establecimiento el 10% del monto de la compra como parte del impuesto de ventas que se deben entregar a hacienda cada final de mes.   La compra  se pueden hacer en línea también, por lo que el usuario debe poder enviar y guardar su identificación para validar que es una persona  real y mayor a 18 años, no se puede vender un automóvil a personas menores de 18 años.

Si el automóvil deseado no se encuentra disponible en la sucursal, el usuario debe de poder visualizar la sucursal más cercana donde se encuentra el automóvil disponible;  en caso de no estar disponible, se debe poder realizar el pedido a la fábrica, y la fábrica debe de indicar cuándo es la fecha más cercana a la entrega.

En cada consulta del product se debe poder visualizar las diferentes fotos y características, además de la distancia del usuario hasta caa una de las sucursales donde se encuentra disponible. El usuario puede ver también los horarios de cada lugar y las personas que trabajan en el  establecimiento.

La base de datos que deben diseñar es una base de datos distribuida donde va a existir el fraccionamiento horizontal y vertical

Deben de hacer la aplicación que cumpla con los objetivos anteriormente descritos.

Deben entregar documentacion de usuario, documentacion técnica, scripts de instalación, descripción del manejo de seguridad, y bitácora de trabajo y evaluación individual  de cada persona en el grupo.
