package modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class FactoryDB_Connection extends DB_Connection{

    private static final String DEFAULT_DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    // Path Josué
    //private static final String DEFAULT_URL = "jdbc:sqlserver://localhost\\FACTORYINSTANCE:51024;database=FactoryDB;user=sa;password=123";
    // Path Jose
    // private static final String DEFAULT_URL = "jdbc:sqlserver://localhost\\FACTORYINSTANCE:51024;database=FactoryDB;user=sa;password=123";
    // Path Leo
    private static final String DEFAULT_URL = "jdbc:sqlserver://localhost\\FACTORY_1:51024;database=FactoryDB;user=sa;password=123";

    private static FactoryDB_Connection DBInstance;

    public static FactoryDB_Connection getFactoryDBInstance(){
        if (DBInstance == null){
            DBInstance = new FactoryDB_Connection();
        }
        return DBInstance;
    }

    public void prueba(){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            ps = connection.prepareCall("SELECT name FROM CarBrands");
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

    public ObservableList<ExtraVehiculo> getCarAccessories(int idCar){
        ObservableList<ExtraVehiculo> ReturnList = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CallableStatement callableStatement;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            callableStatement = connection.prepareCall("{call [dbo].[usp_CarXAccessorySelect](?)}");
            callableStatement.setInt(1, idCar);
            callableStatement.executeQuery();
            rs = callableStatement.getResultSet();
            while (rs.next()) {
                //String id = rs.getString("carXAccessory_id");
                int extraID = rs.getInt("accessorie_id");
                float price = rs.getFloat("price");
                String name = rs.getString("name");
                ReturnList.add(new ExtraVehiculo(extraID, name, String.valueOf(price)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return ReturnList;
    }

    public ArrayList<Marca> getCarBrands(){
        ArrayList<Marca> marcas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CallableStatement callableStatement;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            callableStatement = connection.prepareCall("{call [dbo].[usp_CarBrandsSelect]}");
            //callableStatement.setInt(1, Types.NULL);
            callableStatement.executeQuery();
            rs = callableStatement.getResultSet();
            while (rs.next()){
                int id = rs.getInt("carBrand_id");
                String name = rs.getString("name");
                marcas.add(new Marca(id, name));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return marcas;
    }

    public ArrayList<TipoVehiculo> getCarType(){
        ArrayList<TipoVehiculo> tipos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CallableStatement callableStatement;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            callableStatement = connection.prepareCall("{call [dbo].[usp_CarTypeSelect]}");
            callableStatement.executeQuery();
            rs = callableStatement.getResultSet();
            while (rs.next()){
                int id = rs.getInt("carType_id");
                String name = rs.getString("name");
                tipos.add(new TipoVehiculo(id, name));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return tipos;
    }

    public ArrayList<TipoGasolina> getFuelType(){
        ArrayList<TipoGasolina> tipos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CallableStatement callableStatement;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            callableStatement = connection.prepareCall("{call [dbo].[usp_FuelTypeSelect]}");
            callableStatement.executeQuery();
            rs = callableStatement.getResultSet();
            while (rs.next()){
                int id = rs.getInt("fuelType_id");
                String name = rs.getString("name");
                tipos.add(new TipoGasolina(id, name));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return tipos;
    }

    public ArrayList<Fabrica> getFactory(){
        ArrayList<Fabrica> fabricas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CallableStatement callableStatement;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            callableStatement = connection.prepareCall("{call [dbo].[usp_FactorySelect]}");
            callableStatement.executeQuery();
            rs = callableStatement.getResultSet();
            while (rs.next()){
                int id = rs.getInt("factory_id");
                String name = rs.getString("name");
                fabricas.add(new Fabrica(id, name));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, ps, rs);
        }
        return fabricas;
    }

    public int insertVehiculo(Vehiculo vehiculo, int idFabrica){
        int result = 0;
        Connection connection = null;
        ResultSet rs = null;
        CallableStatement callableStatement = null;
        try {
            connection = getConnection(DEFAULT_DRIVER_CLASS, DEFAULT_URL);
            callableStatement = connection.prepareCall("{call [dbo].[usp_CarInsert](?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setInt(1, vehiculo.getMarcaTipo().getID());
            callableStatement.setInt(2, vehiculo.getTipoVehiculo().getID());
            callableStatement.setNString(3, vehiculo.getModelo());
            callableStatement.setNString(4, vehiculo.getMotor());
            callableStatement.setInt(5, Integer.parseInt(vehiculo.getAnio()));
            callableStatement.setInt(6, Integer.parseInt(vehiculo.getAsientos()));
            callableStatement.setInt(7, Integer.parseInt(vehiculo.getPuertas()));
            callableStatement.setInt(8, vehiculo.getTipoGasolina().getID());
            callableStatement.setFloat(9, Float.parseFloat(vehiculo.getAceleracion()));
            callableStatement.setFloat(10, Float.parseFloat(vehiculo.getVel_maxima()));
            callableStatement.setInt(11, Integer.parseInt(vehiculo.getPrecio()));
            callableStatement.setNull(12, Types.NULL);
            callableStatement.setInt(13, idFabrica);
            callableStatement.setInt(14, Integer.parseInt(vehiculo.getCantidad_en_fabrica()));
            callableStatement.executeQuery();
            rs = callableStatement.getResultSet();
            while (rs.next()) {
                result = rs.getInt("car_id");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeJDBCResources(connection, callableStatement, rs);
        }
        return result;
    }
}
