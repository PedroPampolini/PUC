package com.ti2cc;

import java.sql.*;
public class DAO {
    private Connection conexao;

    DAO(){
        conexao = null;
    }

    public boolean conectar(){
        String driveName = "org.postgresql.Driver";
        String serverName = "localhost";
        String mydatabase = "casa";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
        String username = "admin";
        String password = "admin";
        boolean status = false;

        try {
            Class.forName(driveName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao == null);
            System.out.println("Connection Successfull");
        } catch (ClassNotFoundException e) {
            System.err.println("Connection Failed: Class Not Found Exception");
        } catch (SQLException e){
            System.err.println("Connection Failed: Connection with SQL failed");
        } catch (Exception e){
            System.err.println("Connection Failed: " + e.getMessage());
        }

        return status;
    }

    public boolean close(){
        boolean status = false;

        try {
            conexao.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return status;
    }

    public boolean inserirCasa(Casa casa){
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO casa (id, CPFProprietario, Bairro, Rua, numero, QuantQuartos, QuantBanheiros, QuantVagas, Metragem)"
            + "Values (" + casa.getId() + ", " + casa.getCpfProprietario() + ", '" + casa.getBairro() + "', '" + casa.getRua() + "', " + casa.getNumero() + ", "
            + casa.getQuantQuartos() + ", " + casa.getQuantbanheiros() + ", " + casa.getQuantVagas() + ", " + casa.getMetragem() + ");");
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return status;
    }

    public boolean atualizaCasa(Casa casa){
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            String sql = "UPDATE casa SET id = " + casa.getId() + ", CPFProprietario = " + casa.getCpfProprietario() + ", Bairro = '" + casa.getBairro() + "', Rua =  '" + casa.getRua() + "', numero = " + casa.getNumero() 
            + ", QuantQuartos = " + casa.getQuantQuartos() + ", QuantBanheiros = " + casa.getQuantbanheiros() + ", QuantVagas = " + casa.getQuantVagas() + ", Metragem = " + casa.getMetragem() + " WHERE ID = " + casa.getId();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return status;
    }

    public boolean excluirCasa(int id){
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM casa WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public Casa[] getCasas(){
        Casa[] casas = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM casa");

            if(rs.next()){
                rs.last();
                casas = new Casa[rs.getRow()];
                rs.beforeFirst();

                for(int i = 0; rs.next(); i++){
                    casas[i] = new Casa(rs.getInt("ID"), rs.getInt("CPFProprietario"), rs.getString("Rua"), rs.getString("Bairro"), rs.getInt("numero"),
                    rs.getInt("QuantQuartos"), rs.getInt("QuantVagas"),rs.getInt("QuantBanheiros"),rs.getFloat("Metragem"));
                }
                st.close();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return casas;
    }
}
