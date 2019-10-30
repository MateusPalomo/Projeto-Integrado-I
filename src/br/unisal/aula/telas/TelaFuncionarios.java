/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisal.aula.telas;
import java.sql.*;
import br.unisal.aula.dal.ModuloConexao;
import javax.swing.JOptionPane;
// a linha abaixo importa recursos da biblioteca rs2xml.jar
// serve para fazer a tabela avançada de pesquisa
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Matheus Silva
 */
public class TelaFuncionarios extends javax.swing.JInternalFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaFuncionarios
     */
    public TelaFuncionarios() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
    //Método para adicionar funcionarios
    public void adicionar() {
        String sql = "insert into tbfuncionario(nomefun, cargofun, endfun, cidadefun, fonefun, emailfun)  values(?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtFunNome.getText());
            pst.setString(2, txtFunCargo.getText());
            pst.setString(3, txtFunEndereco.getText());
            pst.setString(4, txtFunCidade.getText());
            pst.setString(5, txtFunFone.getText());
            pst.setString(6, txtFunEmail.getText());
            

            // validação dos campos obrigatorios
            if ((txtFunNome.getText().isEmpty()) || (txtFunCargo.getText().isEmpty()) || (txtFunEndereco.getText().isEmpty()) || (txtFunFone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios!!");

            } else {
                // a linha abaixo atualiza a tabela funcionarios com os dados adicionados no formulario
                // a estrutura abaixo é usada para confirmar os dados adicionados na tabela
                int adicionado = pst.executeUpdate();
                /* Caso o 'adicionado' for (1), o funcionarios foi adicionado com sucesso, 
             * caso for 0 ele não terá sido adicionado por algum erro. 
                 */
                // a linha abaixo serve para entendimento da lógica
                // System.out.println(adicionado); // Se for adicionado sem problemas, estará mostrando o número 1. 
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Funcionario adicionado com sucesso!");
                    // os códigos abaixo estarão apagando os campos do formulario
                    txtFunNome.setText(null);
                    txtFunCargo.setText(null);
                    txtFunEndereco.setText(null);
                    txtFunCidade.setText(null);
                    txtFunFone.setText(null);
                    txtFunEmail.setText(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Por algum motivo o funcionario não foi cadastrado!!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     // Método para pesquisar clientes pelo nome, com filtro!
    // Consulta Avançada *** 
    private void pesquisar_funcionarios() {
        String sql = "select * from tbfuncionario where nomefun like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa (txtFunPesquisar) para o > "?" <
            // atenção ao % que é a continuação do comando SQL (falta a % para o código funcionar no MYSQL)
            pst.setString(1, txtFunPesquisar.getText() + "%"); // + % para funcionar o comando sql
            rs = pst.executeQuery();
            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblFuncionarios.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     // métodos para setar os campos do formulario com o couteudo da tabela
    public void setar_campos(){
        int setar = tblFuncionarios.getSelectedRow();
        txtFunID.setText(tblFuncionarios.getModel().getValueAt(setar, 0).toString());
        txtFunNome.setText(tblFuncionarios.getModel().getValueAt(setar, 1).toString());
        txtFunCargo.setText(tblFuncionarios.getModel().getValueAt(setar, 2).toString());
        txtFunEndereco.setText(tblFuncionarios.getModel().getValueAt(setar, 3).toString());
        txtFunCidade.setText(tblFuncionarios.getModel().getValueAt(setar, 4).toString());
        txtFunFone.setText(tblFuncionarios.getModel().getValueAt(setar, 5).toString());
        txtFunEmail.setText(tblFuncionarios.getModel().getValueAt(setar, 6).toString());
        
        // a linha abaixo desabilita o botão adicionar
        // assim não tem como adicionar um usuario já cadastrado
        btnAdicionar.setEnabled(false);
        
    }
    
    // método para alterar os funcionarios
    public void alterar() {
        String sql = "update tbfuncionario set nomefun = ?, cargofun = ?, endfun = ?, cidadefun = ?, fonefun = ?, emailfun = ?  where idfun=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtFunNome.getText());
            pst.setString(2, txtFunCargo.getText());
            pst.setString(3, txtFunEndereco.getText());
            pst.setString(4, txtFunCidade.getText());
            pst.setString(5, txtFunFone.getText());
            pst.setString(6, txtFunEmail.getText());
            pst.setString(7, txtFunID.getText());
            
            // validação dos campos obrigatorios
            if ((txtFunNome.getText().isEmpty()) || (txtFunCargo.getText().isEmpty()) || (txtFunEndereco.getText().isEmpty()) || (txtFunFone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios!!");

            } else {
                // a linha abaixo atualiza a tabela clientes
                // a estrutura abaixo é usada para confirmar os dados adicionados na tabela
                int adicionado = pst.executeUpdate();
                /* Caso o 'adicionado' for (1), o funcionario foi adicionado com sucesso, 
                 * caso for 0 ele não terá sido adicionado por algum erro. 
                 */
                // a linha abaixo serve para entendimento da lógica
                // System.out.println(adicionado); // Se for adicionado sem problemas, estará mostrando o número 1. 
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do funcionario alterados com sucesso!!!");
                    // os códigos abaixo estarão apagando os campos do formulario
                    txtFunID.setText(null);
                    txtFunNome.setText(null);
                    txtFunCargo.setText(null);
                    txtFunEndereco.setText(null);
                    txtFunCidade.setText(null);
                    txtFunFone.setText(null);
                    txtFunEmail.setText(null);
                    btnAdicionar.setEnabled(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     // Método responsavel pela remoção de funcionarios
    public void remover(){
        // a estrutura abaixo confirma a remoção do usuario
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuario?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbfuncionario where idfun = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtFunID.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0 ){
                    JOptionPane.showMessageDialog(null, "Usuario removido com sucesso!");
                    txtFunID.setText(null);
                    txtFunNome.setText(null);
                    txtFunCargo.setText(null);
                    txtFunEndereco.setText(null);
                    txtFunCidade.setText(null);
                    txtFunFone.setText(null);
                    txtFunEmail.setText(null);
                    btnAdicionar.setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtFunPesquisar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFuncionarios = new javax.swing.JTable();
        txtFunNome = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtFunCidade = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtFunID = new javax.swing.JTextField();
        txtFunFone = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtFunEmail = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        txtFunEndereco = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnDeletar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtFunCargo = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setMaximumSize(new java.awt.Dimension(702, 489));
        setMinimumSize(new java.awt.Dimension(702, 489));

        jLabel2.setText("Endereço*:");

        txtFunPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFunPesquisarMouseClicked(evt);
            }
        });
        txtFunPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFunPesquisarActionPerformed(evt);
            }
        });
        txtFunPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFunPesquisarKeyReleased(evt);
            }
        });

        jLabel3.setText("Telefone*:");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/unisal/aula/icones/search.png"))); // NOI18N
        jLabel5.setToolTipText("Consultar");

        jLabel4.setText("E-mail:");

        tblFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblFuncionarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFuncionariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblFuncionarios);

        jLabel10.setText("ID Funcionario:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Cadastro de Funcionarios");

        txtFunID.setEnabled(false);

        txtFunFone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFunFoneKeyTyped(evt);
            }
        });

        jLabel6.setText("Cidade:");

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projetointegrado/img/add.png"))); // NOI18N
        btnAdicionar.setToolTipText("Adicionar");
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projetointegrado/img/update.png"))); // NOI18N
        btnAlterar.setToolTipText("Editar");
        btnAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlterar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        jLabel1.setText("Nome*:");

        btnDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projetointegrado/img/delete.png"))); // NOI18N
        btnDeletar.setToolTipText("Deletar");
        btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        jLabel7.setText("* = Campos Obrigatórios");

        jLabel8.setText("Cargo*:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel10))
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFunFone, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFunEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtFunID, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtFunNome, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtFunEndereco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtFunCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtFunCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addGap(56, 56, 56))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtFunPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(162, 162, 162))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(5, 5, 5)
                        .addComponent(txtFunPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtFunID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtFunNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtFunCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFunCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtFunEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFunFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtFunEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(btnDeletar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                .addGap(97, 97, 97))
        );

        setBounds(0, 0, 702, 489);
    }// </editor-fold>//GEN-END:initComponents

    private void txtFunPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFunPesquisarMouseClicked
        // chamando o método para setar os campos
        setar_campos();
    }//GEN-LAST:event_txtFunPesquisarMouseClicked

    private void txtFunPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFunPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFunPesquisarActionPerformed

    private void txtFunPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFunPesquisarKeyReleased
        // chama o método pesquisar clientes
        pesquisar_funcionarios();
    }//GEN-LAST:event_txtFunPesquisarKeyReleased

    private void tblFuncionariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFuncionariosMouseClicked
        // chamando o método para setar os campos
        setar_campos();
    }//GEN-LAST:event_tblFuncionariosMouseClicked

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // Adicionar -- Chamar o método adicionar
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // Alterar -- chamar medtodo alterar
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        // Remover -- chamar o método remover
        remover();
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void txtFunFoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFunFoneKeyTyped
        // Apenas Números
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtFunFoneKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblFuncionarios;
    private javax.swing.JTextField txtFunCargo;
    private javax.swing.JTextField txtFunCidade;
    private javax.swing.JTextField txtFunEmail;
    private javax.swing.JTextField txtFunEndereco;
    private javax.swing.JTextField txtFunFone;
    private javax.swing.JTextField txtFunID;
    private javax.swing.JTextField txtFunNome;
    private javax.swing.JTextField txtFunPesquisar;
    // End of variables declaration//GEN-END:variables
}
