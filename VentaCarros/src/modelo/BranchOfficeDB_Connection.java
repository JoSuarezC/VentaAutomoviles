package modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class BranchOfficeDB_Connection extends DB_Connection{
    private static final String DEFAULT_DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    // Path Josué
    private static final String DEFAULT_URL = "jdbc:sqlserver://localhost\\BOFFICE_INSTANCE:50449;database=BranchOfficeDB;user=sa;password=123";
    // Path Jose ** Cambiar
    //private static final String DEFAULT_URL = "jdbc:sqlserver://localhost\\BOFFICE_INSTANCE:50449;database=BranchOfficeDB;user=sa;password=123";
    private static BranchOfficeDB_Connection DBInstance;

    public static BranchOfficeDB_Connection getHSDBInstance(){
        if (DBInstance == null){
            DBInstance = new BranchOfficeDB_Connection();
        }
        return DBInstance;
    }

    public void prueba(){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            ps = connection.prepareCall("SELECT name FROM Country");
            ps.execute();
            rs = ps.getResultSet();
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println(name);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
    }

    public ObservableList<Vehiculo> SelectAutosXSucursal(int idSucursal){
        ObservableList<Vehiculo> ReturnList = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CallableStatement callableStatement;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            callableStatement = connection.prepareCall("{call [dbo].[usp_Car-StockSelect](?)}");
            callableStatement.setInt(1, idSucursal);
            callableStatement.executeQuery();
            rs = callableStatement.getResultSet();
            while (rs.next()) {
                String id = rs.getString("car_id");
                String marca = rs.getString("brand");
                String nombre = rs.getString("name");
                String modelo = rs.getString("model");
                String annio = rs.getString("year");
                String num_pasajeros = rs.getString("seats");
                String tipo = rs.getString("name");
                String motor = rs.getString("engine");
                String asientos = rs.getString("seats");
                String puertas = rs.getString("doors");
                String gasolina = rs.getString("fuel");
                String aceleracion = rs.getString("acceleration");
                String vel_maxima = rs.getString("maximum_speed");
                String precio = rs.getString("price");
                Vehiculo CarroAux = new Vehiculo(id, marca, modelo, annio, num_pasajeros, tipo, motor,
                        asientos, puertas, gasolina, aceleracion, vel_maxima, precio);
                ReturnList.add(CarroAux);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return ReturnList;
    }

    public ObservableList<MetodoPago> getPaymentMethods(){
        ObservableList<MetodoPago> ReturnList = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CallableStatement callableStatement;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            callableStatement = connection.prepareCall("{call [dbo].[usp_PaymentMethodSelect]}");
            callableStatement.executeQuery();
            rs = callableStatement.getResultSet();
            while (rs.next()) {
                int methodID = rs.getInt("paymentMethod_id");
                String name = rs.getString("name");
                ReturnList.add(new MetodoPago(methodID, name));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return ReturnList;
    }

    public ObservableList<PlanDePago> getCreditPlan(PedidoVehiculo pedidoVehiculo){
        ObservableList<PlanDePago> ReturnList = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CallableStatement callableStatement;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            callableStatement = connection.prepareCall("{call [dbo].[usp_CreditPlanSelect]}");
            callableStatement.executeQuery();
            rs = callableStatement.getResultSet();
            while (rs.next()) {
                int creditPlan_id = rs.getInt("creditPlan_id");
                float prima = rs.getFloat("prima");
                float interest = rs.getFloat("interest");
                float anualTerm = rs.getFloat("anualTerm");
                String name = rs.getString("planName");
                ReturnList.add(new PlanDePago(creditPlan_id, name, prima, anualTerm, interest, (int)pedidoVehiculo.getPrecioTotal()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return ReturnList;
    }

    public int generarCompra(Usuario usuario, MetodoPago metodoPago, int idSucursal, int cantidadTotal, int pago, int estadoOrden){
        Connection connection = null;
        ResultSet rs = null;
        int result = 0;
        CallableStatement ps = null;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            ps = connection.prepareCall("{call dbo.[usp_SalesOrderInsert](?, ?, ?, ?, ?, ?)}");
            ps.setInt(1, usuario.getIdUsuario());
            ps.setInt(2, metodoPago.getIdMethod());
            ps.setInt(3, idSucursal);
            ps.setInt(4, cantidadTotal);
            ps.setInt(5, pago);
            ps.setInt(6, estadoOrden);
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                result = rs.getInt("salesOrder_id");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return result;
    }

    public int agregarProductoACompra(int idCarroVendido, float costeTotal, int ordenCompra){
        Connection connection = null;
        ResultSet rs = null;
        int result = 0;
        CallableStatement ps = null;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            ps = connection.prepareCall("{call dbo.[usp_SalesOrderDetailsInsert](?,?,?,?)}");
            ps.setInt(1, ordenCompra);
            ps.setInt(2, idCarroVendido);
            ps.setInt(3, 1);
            ps.setFloat(4, costeTotal);
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                result = rs.getInt("orderDetails_id");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return result;
    }

    public int generarCarroVendido(PedidoVehiculo pedidoVehiculo){
        Connection connection = null;
        ResultSet rs = null;
        int result = 0;
        CallableStatement ps = null;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            ps = connection.prepareCall("{call dbo.[usp_CarSoldInsert](?)}");
            ps.setInt(1, Integer.parseInt(pedidoVehiculo.getVehiculo().getID()));
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                result = rs.getInt("car_sold_id");
                System.out.println(pedidoVehiculo.getExtrasVehiculo());
               // if(pedidoVehiculo.getExtrasVehiculo() != null){
                    for(ExtraVehiculo extra: pedidoVehiculo.getExtrasVehiculo())
                        agregarAccesoriosCarroVendido(result, extra);
                //}
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return result;
    }

    public int agregarAccesoriosCarroVendido(int idCarroVendido, ExtraVehiculo extraVehiculo){
        Connection connection = null;
        ResultSet rs = null;
        int result = 0;
        CallableStatement ps = null;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            ps = connection.prepareCall("{call dbo.[usp_CarSold-AccessoryInsert](?,?)}");
            ps.setInt(1, idCarroVendido);
            ps.setInt(2, extraVehiculo.getIdExtra());
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                result = rs.getInt("line");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return result;
    }

    public int generarCredito(int idCompra, PlanDePago planDePago){
        Connection connection = null;
        ResultSet rs = null;
        int result = 0;
        CallableStatement ps = null;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            ps = connection.prepareCall("{call dbo.[usp_CreditGivenInsert](?,?,?,?)}");
            ps.setInt(1, idCompra);
            ps.setInt(2, planDePago.getPlanID());
            ps.setInt(3, planDePago.getTotal_a_pagar());
            ps.setFloat(4, planDePago.getPago_por_mes());
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                result = rs.getInt("credit_id");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return result;
    }

    public ObservableList<Sucursal> getSucursales(){
        ObservableList<Sucursal> ReturnList = FXCollections.observableArrayList();
        Connection connection = null;
        ResultSet rs = null;
        CallableStatement ps = null;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            ps = connection.prepareCall("{call [dbo].[usp_BranchOfficeSelectAll]}");
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                int branchOffice_id = rs.getInt("branchOffice_id");
                String branchoffice_name = rs.getString("name");
                int country_id = rs.getInt("country_id");
                String countryName = rs.getString("countryName");
                String horaApertura = rs.getString("horaApertura");
                String horaCierre = rs.getString("horaCierre");
                ReturnList.add(new Sucursal(branchOffice_id, branchoffice_name, countryName, country_id, horaApertura, horaCierre));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return ReturnList;
    }

}
