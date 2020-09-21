/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuPrincipal;

import GUIs.GUIEquipamento;
import GUIs.GUIPessoa;
import GUIs.GUIApartamento;
import GUIs.GUISetor;
import GUIs.GUITipoAcomodacao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import tools.Centraliza;
import tools.ManipulaImagem;

class MenuPrincipal extends JFrame {

    private JFrame cp = new JFrame();

    private JToolBar toolbar = new JToolBar();

    private JButton acomodacao;
    private JButton equipamentos;
    private JButton setores;
    private JButton TipoAcomodacao;
    private JButton pessoa;

    private JPanel p;

    private ManipulaImagem manipulaimagem = new ManipulaImagem();

    public MenuPrincipal() {

        cp.getContentPane();
        cp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cp.setTitle("Menu Principal");
        cp.setSize(700, 700);
        cp.setLayout(new BorderLayout());
        setLocationRelativeTo(null);//centro do monitor

        Centraliza centraliza = new Centraliza();
        centraliza.centralizaComponente(cp);

        acomodacao = manipulaimagem.insereBotao(manipulaimagem.criaIcon("../imagenscrud/Acomodacao2.jpg", 80, 80), "Acomodação");
        equipamentos = manipulaimagem.insereBotao(manipulaimagem.criaIcon("../imagenscrud/equipamentos.png", 80, 80), "Equipamentos");
        setores = manipulaimagem.insereBotao(manipulaimagem.criaIcon("../imagenscrud/setores.png", 80, 80), "Setores");
        TipoAcomodacao = manipulaimagem.insereBotao(manipulaimagem.criaIcon("../imagenscrud/Acomodacao.jpg", 80, 80), "Tipo da Acomodação");
        pessoa = manipulaimagem.insereBotao(manipulaimagem.criaIcon("../imagenscrud/Acomodacao.jpg", 80, 80), "Pessoa");
        toolbar.add(equipamentos);
        toolbar.add(pessoa);
        toolbar.add(setores);
        toolbar.add(TipoAcomodacao);
        toolbar.add(acomodacao);
        toolbar.setBackground(Color.WHITE);

        p = new JPanel();
        p.setLayout(new GridLayout(1, 1));
        p.setBackground(Color.WHITE);

        cp.add(toolbar, BorderLayout.NORTH);

        cp.add(p, BorderLayout.CENTER);

        equipamentos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIEquipamento guiapequipamentos = new GUIEquipamento(cp);
            }
        });
        setores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUISetor guisetores = new GUISetor(cp);
            }
        });
        TipoAcomodacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUITipoAcomodacao guiacomodacoes = new GUITipoAcomodacao(cp);
            }
        });
        acomodacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIApartamento guiacomodacao = new GUIApartamento(cp);
            }
        });
        pessoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIPessoa guipessoa = new GUIPessoa(cp);
            }
        });

        cp.setVisible(true);

    }

}
