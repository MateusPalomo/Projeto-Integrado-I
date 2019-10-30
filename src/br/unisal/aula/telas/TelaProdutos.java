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
public class TelaProdutos extends javax.swing.JInternalFrame {
        
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaProdutos
     */
    public TelaProdutos() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
    //Método para adicionar produtos
    public void adicionar() {
        String sql = "insert into tbproduto(nomeprod, categoria, modelo, marca, preço, quantidade)  values(?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtProdNome.getText());
            pst.setString(2, txtProdCategoria.getText());
            pst.setString(3, txtProdModelo.getText());
            pst.setString(4, txtProdMarca.getText());
            pst.setString(5, txtProdPreco.getText());
            pst.setString(6, txtProdQuantidade.getText());
            

            // validação dos campos obrigatorios
            if ((txtProdNome.getText().isEmpty()) || (txtProdPreco.getText().isEmpty()) || (txtProdQuantidade.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios!!");

            } else {
                // a linha abaixo atualiza a tabela usuarios com os dados adicionados no formulario
                // a estrutura abaixo é usada para confirmar os dados adicionados na tabela
                int adicionado = pst.executeUpdate();
                /* Caso o 'adicionado' for (1), o produto foi adicionado com sucesso, 
             * caso for 0 ele não terá sido adicionado por algum erro. 
                 */
                // a linha abaixo serve para entendimento da lógica
                // System.out.println(adicionado); // Se for adicionado sem problemas, estará mostrando o número 1. 
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso!");
                    // os códigos abaixo estarão apagando os campos do formulario
                    txtProdNome.setText(null);
                    txtProdCategoria.setText(null);
                    txtProdModelo.setText(null);
                    txtProdMarca.setText(null);
                    txtProdPreco.setText(null);
                    txtProdQuantidade.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     // Método para pesquisar clientes pelo nome, com filtro!
    // Consulta Avançada *** 
    private void pesquisar_produtos() {
        String sql = "select * from tbproduto where nomeprod like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa (txtProdPesquisar) para o > "?" <
            // atenção ao % que é a continuação do comando SQL (falta a % para o código funcionar no MYSQL)
            pst.setString(1, txtProdPesquisar.getText() + "%"); // + % para funcionar o comando sql
            rs = pst.executeQuery();
            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblProdutos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // métodos para setar os campos do formulario com o couteudo da tabela
    public void setar_campos(){
        int setar = tblProdutos.getSelectedRow();
        txtProdID.setText(tblProdutos.getModel().getValueAt(setar, 0).toString());
        txtProdNome.setText(tblProdutos.getModel().getValueAt(setar, 1).toString());
        txtProdCategoria.setText(tblProdutos.getModel().getValueAt(setar, 2).toString());
        txtProdModelo.setText(tblProdutos.getModel().getValueAt(setar, 3).toString());
        txtProdMarca.setText(tblProdutos.getModel().getValueAt(setar, 4).toString());
        txtProdPreco.setText(tblProdutos.getModel().getValueAt(setar, 5).toString());
        txtProdQuantidade.setText(tblProdutos.getModel().getValueAt(setar, 6).toString());
        
        // a linha abaixo desabilita o botão adicionar
        // assim não tem como adicionar um usuario já cadastrado
        btnAdicionar.setEnabled(false);
        
    }
    
    // método para alterar os produtos
    public void alterar() {
        String sql = "update tbproduto set nomeprod = ? , categoria = ?, modelo = ?, marca = ?, preço = ?, quantidade = ?  where idprod=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtProdNome.getText());
            pst.setString(2, txtProdCategoria.getText());
            pst.setString(3, txtProdModelo.getText());
            pst.setString(4, txtProdMarca.getText());
            pst.setString(5, txtProdPreco.getText());
            pst.setString(6, txtProdQuantidade.getText());
            pst.setString(7, txtProdID.getText());
            
            // validação dos campos obrigatorios
            if ((txtProdNome.getText().isEmpty()) || (txtProdPreco.getText().isEmpty()) || (txtProdQuantidade.getText().isEmpty())) {
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
                    txtProdID.setText(null);
                    txtProdNome.setText(null);
                    txtProdCategoria.setText(null);
                    txtProdModelo.setText(null);
                    txtProdMarca.setText(null);
                    txtProdPreco.setText(null);
                    txtProdQuantidade.setText(null);
                    btnAdicionar.setEnabled(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     // Método responsavel pela remoção de produtos
    public void remover(){
        // a estrutura abaixo confirma a remoção do usuario
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este produto?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbproduto where idprod = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtProdID.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0 ){
                    JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
                    txtProdID.setText(null);
                    txtProdNome.setText(null);
                    txtProdCategoria.setText(null);
                    txtProdModelo.setText(null);
                    txtProdMarca.setText(null);
                    txtProdPreco.setText(null);
                    txtProdQuantidade.setText(null);
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

        txtProdPesquisar = new javax.swing.JTextField();
        txtProdPreco = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtProdCategoria = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtProdQuantidade = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        txtProdModelo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnDeletar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtProdNome = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtProdMarca = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtProdID = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setMaximumSize(new java.awt.Dimension(702, 489));
        setMinimumSize(new java.awt.Dimension(702, 489));
        setPreferredSize(new java.awt.Dimension(702, 489));

        txtProdPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtProdPesquisarMouseClicked(evt);
            }
        });
        txtProdPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProdPesquisarActionPerformed(evt);
            }
        });
        txtProdPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProdPesquisarKeyReleased(evt);
            }
        });

        txtProdPreco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProdPrecoKeyTyped(evt);
            }
        });

        jLabel3.setText("Preço*:");

        jLabel6.setText("Marca:");

        txtProdQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProdQuantidadeKeyTyped(evt);
            }
        });

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

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/unisal/aula/icones/search.png"))); // NOI18N
        jLabel5.setToolTipText("Consultar");

        btnDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projetointegrado/img/delete.png"))); // NOI18N
        btnDeletar.setToolTipText("Deletar");
        btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        jLabel4.setText("Quantidade*:");

        jLabel7.setText("* = Campos Obrigatórios");

        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProdutos);

        jLabel8.setText("Categoria: ");

        jLabel10.setText("ID do Produto:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Cadastro de Produtos");

        jLabel2.setText("Modelo:");

        txtProdID.setEnabled(false);

        jLabel11.setText("R$");

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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel10))
                                        .addGap(35, 35, 35))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtProdID)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(txtProdNome, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtProdModelo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel8)
                                                    .addComponent(jLabel6))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtProdMarca)
                                                    .addComponent(txtProdCategoria)))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtProdPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtProdQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(7, 7, 7)))))
                        .addGap(56, 56, 56))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtProdPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                        .addComponent(txtProdPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtProdID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtProdNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtProdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtProdMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtProdModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProdPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtProdQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                    .addComponent(btnDeletar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                .addGap(97, 97, 97))
        );

        setBounds(0, 0, 702, 489);
    }// </editor-fold>//GEN-END:initComponents

    private void txtProdPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtProdPesquisarMouseClicked
        // chamando o método para setar os campos
        setar_campos();
    }//GEN-LAST:event_txtProdPesquisarMouseClicked

    private void txtProdPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProdPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProdPesquisarActionPerformed

    private void txtProdPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProdPesquisarKeyReleased
        // chama o método pesquisar clientes
        pesquisar_produtos();
    }//GEN-LAST:event_txtProdPesquisarKeyReleased

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

    private void tblProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutosMouseClicked
        // chamando o método para setar os campos
        setar_campos();
    }//GEN-LAST:event_tblProdutosMouseClicked

    private void txtProdPrecoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProdPrecoKeyTyped
        // Apenas Números
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtProdPrecoKeyTyped

    private void txtProdQuantidadeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProdQuantidadeKeyTyped
        // Apenas Números
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtProdQuantidadeKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTextField txtProdCategoria;
    private javax.swing.JTextField txtProdID;
    private javax.swing.JTextField txtProdMarca;
    private javax.swing.JTextField txtProdModelo;
    private javax.swing.JTextField txtProdNome;
    private javax.swing.JTextField txtProdPesquisar;
    private javax.swing.JTextField txtProdPreco;
    private javax.swing.JTextField txtProdQuantidade;
    // End of variables declaration//GEN-END:variables
}
