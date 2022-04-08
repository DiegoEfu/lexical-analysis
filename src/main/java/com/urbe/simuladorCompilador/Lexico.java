/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.urbe.simuladorCompilador;

import javax.swing.JTextArea;

/**
 * @author carlos
 * @author diego
 * @author andrés
 */
public class Lexico extends javax.swing.JFrame {

    /**
     * Creates new form Lexico
     */
    private final String fuente;
    public Lexico(String fuente) {
        this.fuente = fuente;
        initComponents();
        escanear();
    }

    private Lexico() {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void escanear(){
        //TODO: realizar análisis léxico. Útiles: java.util.regex.Matcher, java.util.StringBuilder
        //TODO: al hacerlo, cada acción va en su método privado aparte.
        // 1. Colocar los comentarios (lineas empezadas en #) en su respectivo TextArea.
        reunirComentarios();
        // 2. Colocar la fuente sin comentarios (NO MUTAR LA FUENTE) en su respectivo TextArea.
        String fuenteModificada = generarFuenteSinComentarios();
        // 3. Contar espacios y caracteres y colocar su cuenta en los TextFields.
        contarEspacios(fuenteModificada);
        // 4. Contar retornos (\n), tabuladores (\t) y colocar su cuenta en los TextFields.
        contarRetornos(fuenteModificada);
        contarTabuladores(fuente);
        // 5. Colocar en los ComboBox las letras, numeros y errores (caracteres invalidos) encontrados.
        separarLetras(fuenteModificada);
        separarNumeros(fuenteModificada);
        separarCI(fuenteModificada);
        // 6. Hacer lo mismo de la 5 pero con las listas restantes.
        separarOper(fuenteModificada);
        separarEsp(fuenteModificada);
        separarSep(fuenteModificada);

        // 7. Resto.
        contarCaracteres(fuenteModificada);
        contarNumeros(fuenteModificada);
        contarEspeciales(fuenteModificada);
        contarErrores(fuenteModificada);
        generarFuenteSinEspacios(fuenteModificada);
    }

    private void contarErrores(String fuenteModificada) {
        int n = errores.getItemCount();
        Integer cuenta = 0;

        for(int i = 0; i < n; i++)
            cuenta = cuenta + errores.getItemAt(i).length();
        
        erroresContador.setText(cuenta.toString());
    }

    private void contarEspeciales(String fuenteModificada) {
        int n = especiales.getItemCount();
        Integer cuenta = 0;

        for(int i = 0; i < n; i++)
            cuenta = cuenta + especiales.getItemAt(i).length();
        
        n = operadores.getItemCount();
        for(int i = 0; i < n; i++)
            cuenta = cuenta + operadores.getItemAt(i).length();
        
        n = separadores.getItemCount();
        for(int i = 0; i < n; i++)
            cuenta = cuenta + separadores.getItemAt(i).length();  
        
        especialesContador.setText(cuenta.toString());
    }

    private void contarNumeros(String fuenteModificada) {
        int n = numeros.getItemCount();
        Integer cuenta = 0;

        for(int i = 0; i < n; i++)
            cuenta = cuenta + numeros.getItemAt(i).length();
        
        numerosContador.setText(cuenta.toString());
    }

    private void contarCaracteres(String fuenteModificada) {
        int n = letras.getItemCount();
        Integer cuenta = 0;

        for(int i = 0; i < n; i++)
            cuenta = cuenta + letras.getItemAt(i).length();
        
        caracteres.setText(cuenta.toString());
    }

    private void contarEspacios(String fuenteSinComentarios) {
        Integer n = 0;
        String[] caracteres = fuenteSinComentarios.split("");
        for(String c : caracteres){
            System.out.print(c);
            if(c.equals(" "))
                n += 1;
        }
            
        espacios.setText(n > 0 ? n.toString() : "0");
    }

    private void reunirComentarios(){
        String[] lineas = fuente.split("\n");
        for(String linea : lineas){
            linea = linea.trim();
            if(!linea.contains("#"))
                continue;
        
            linea = linea.substring(linea.indexOf("#"), linea.length());
            comentarios.setText(comentarios.getText() + linea + "\n");
        }
    }

    private String generarFuenteSinComentarios(){
        String[] lineas = fuente.split("\n");
        String fuenteSinC = "";

        for(String linea : lineas){
            Integer indice = linea.indexOf("#");
            indice = indice == -1 ? linea.length() : indice;
            linea = linea.substring(0, indice);
            linea = linea.trim();

            fuenteSinC = fuenteSinComentarios.getText() + linea + "\n";
            fuenteSinComentarios.setText(fuenteSinC);
        }
        return fuenteSinC;
    }
    private void generarFuenteSinEspacios(String fuente){
        String[] lineas = fuente.split("\n");
        String fuenteSinE = "";
   
        for(String linea : lineas){
            linea = linea.replaceAll(" ", "");
            fuenteSinE += linea + "\n";
        }
        
        fuenteSinEspacios.setText(fuenteSinE);
    }
            
    private void contarRetornos(String str) {
		Integer lineas = str.split("[\n|\r]").length - 1;
		retornos.setText(lineas > 0 ? lineas.toString() : "0");
    }

    private void contarTabuladores(String str) {
    	Integer aux = 0;
    	aux = str.split("\t").length - 1;
    	tabuladores.setText(aux > 0 ? aux.toString() : "0");
    }

    private void separarLetras(String str) {
    	String[] texto = str.split("\n");
    	String aux = "";
    	for (int i = 0; i < texto.length; i++) {
    		for (int j = 0; j < texto[i].length(); j++) {
    			if (Character.isLetter(texto[i].charAt(j))) {
    				aux = aux + texto[i].charAt(j);
    			} 
    		}
            if(aux.toString() != "")
    		    letras.addItem(aux.toString());
    		aux = "";
    	}
    }

    private void separarNumeros(String str) {
    	String[] texto = str.split("\n");
    	String aux = "";
    	for (int i = 0; i < texto.length; i++) {
    		for (int j = 0; j < texto[i].length(); j++) {
    			if (Character.isDigit(texto[i].charAt(j))) {
    				aux = aux + texto[i].charAt(j);
    			} 
    		}
    		if(aux.toString() != "")
    		    numeros.addItem(aux.toString());
    		aux = "";
    	}
    }

    private void separarCI(String str) {
    	String[] texto = str.split("\n");
    	String CI = "&'#$´`°~";
    	String aux = "";
    	for (int i = 0; i < texto.length; i++) {
    		for (int j = 0; j < texto[i].length(); j++) {
    			for (int x = 0; x < CI.length(); x++) {
    				if (texto[i].charAt(j) == CI.charAt(x)) {
        				aux = aux + texto[i].charAt(j);
        			} 
    			}
    		}
    		if(aux.toString() != "")
    		    errores.addItem(aux.toString());
    		aux = "";
    	}
    }

    private void separarOper(String str) {
    	String[] texto = str.split("\n");
    	String CI = "%/=*+^-";
    	String aux = "";
    	for (int i = 0; i < texto.length; i++) {
    		for (int j = 0; j < texto[i].length(); j++) {
    			for (int x = 0; x < CI.length(); x++) {
    				if (texto[i].charAt(j) == CI.charAt(x)) {
        				aux = aux + texto[i].charAt(j);
        			} 
    			}
    		}
            if(aux.toString() != "")
    		    operadores.addItem(aux.toString());
    		aux = "";
    	}
    }

    private void separarEsp(String str) {
    	String[] texto = str.split("\n");
    	String CI = "\"¿?¡!_@";
    	String aux = "";
    	for (int i = 0; i < texto.length; i++) {
    		for (int j = 0; j < texto[i].length(); j++) {
    			for (int x = 0; x < CI.length(); x++) {
    				if (texto[i].charAt(j) == CI.charAt(x)) {
        				aux = aux + texto[i].charAt(j);
        			} 
    			}
    		}
            if(aux.toString() != "")
    		    especiales.addItem(aux.toString());
    		aux = "";
    	}
    }
    
    private void separarSep(String str) {
    	String[] texto = str.split("\n");
    	String CI = "()[]{},;.:|";
    	String aux = "";
    	for (int i = 0; i < texto.length; i++) {
    		for (int j = 0; j < texto[i].length(); j++) {
    			for (int x = 0; x < CI.length(); x++) {
    				if (texto[i].charAt(j) == CI.charAt(x)) {
        				aux = aux + texto[i].charAt(j);
        			} 
    			}
    		}
            if(aux.toString() != "")
    		    separadores.addItem(aux.toString());
    		aux = "";
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        comentarios = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        fuenteSinEspacios = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        fuenteSinComentarios = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        espacios = new javax.swing.JTextField();
        caracteres = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        retornos = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tabuladores = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        letras = new javax.swing.JComboBox<>();
        numeros = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        errores = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        especiales = new javax.swing.JComboBox<>();
        operadores = new javax.swing.JComboBox<>();
        separadores = new javax.swing.JComboBox<>();
        volver = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        numerosContador = new javax.swing.JTextField();
        especialesContador = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        erroresContador = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Comentarios: ");

        comentarios.setColumns(20);
        comentarios.setRows(5);
        jScrollPane1.setViewportView(comentarios);
        comentarios.setEditable(false);

        fuenteSinEspacios.setColumns(20);
        fuenteSinEspacios.setRows(5);
        jScrollPane2.setViewportView(fuenteSinEspacios);
        fuenteSinEspacios.setEditable(false);

        fuenteSinComentarios.setColumns(20);
        fuenteSinComentarios.setRows(5);
        jScrollPane3.setViewportView(fuenteSinComentarios);
        fuenteSinComentarios.setEditable(false);

        jLabel2.setText("Fuente sin Comentarios:");

        jLabel3.setText("Fuente sin Espacios:");

        jLabel4.setText("Espacios en Blanco:");

        espacios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                espaciosActionPerformed(evt);
            }
        });

        caracteres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caracteresActionPerformed(evt);
            }
        });

        jLabel5.setText("Letras:");

        retornos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retornosActionPerformed(evt);
            }
        });

        jLabel6.setText("Retornos y saltos:");

        tabuladores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabuladoresActionPerformed(evt);
            }
        });

        jLabel7.setText("Números:");

        jLabel8.setText("Letras:");

        letras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));

        numeros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));

        jLabel9.setText("Números:");

        errores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));

        jLabel10.setText("Errores:");

        jLabel11.setText("Especiales:");

        jLabel12.setText("Separadores: ");

        jLabel13.setText("Operadores:");

        especiales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {  }));

        operadores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {  }));

        separadores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {  }));

        volver.setText("← Volver al Editor");
        volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverActionPerformed(evt);
            }
        });

        jLabel14.setText("Tabuladores:");

        numerosContador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numerosContadorActionPerformed(evt);
            }
        });

        especialesContador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                especialesContadorActionPerformed(evt);
            }
        });

        jLabel15.setText("Especiales:");

        jLabel16.setText("Errores:");

        erroresContador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                erroresContadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(espacios, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(numerosContador, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(caracteres, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(especialesContador, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(retornos, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(erroresContador, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tabuladores, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(12, 12, 12))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(43, 43, 43))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(volver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(letras, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(errores, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(numeros, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(especiales, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(operadores, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(separadores, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 130, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(volver)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(espacios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(numerosContador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(especialesContador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel5)
                        .addComponent(caracteres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(retornos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel16)
                                .addComponent(erroresContador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tabuladores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(letras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(operadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(numeros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(separadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(errores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(especiales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        espacios.setEditable(false);
        caracteres.setEditable(false);
        retornos.setEditable(false);
        tabuladores.setEditable(false);
        letras.setEditable(false);
        numeros.setEditable(false);
        errores.setEditable(false);
        especiales.setEditable(false);
        operadores.setEditable(false);
        separadores.setEditable(false);
        caracteres.setEditable(false);
        caracteres.setEditable(false);
        caracteres.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabuladoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabuladoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabuladoresActionPerformed

    private void retornosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retornosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_retornosActionPerformed

    private void caracteresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caracteresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_caracteresActionPerformed

    private void espaciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_espaciosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_espaciosActionPerformed

    private void volverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverActionPerformed
        Simulador l = new Simulador(fuente);
        l.setVisible(true);
        dispose();
    }//GEN-LAST:event_volverActionPerformed

    private void numerosContadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numerosContadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numerosContadorActionPerformed

    private void especialesContadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_especialesContadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_especialesContadorActionPerformed

    private void erroresContadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_erroresContadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_erroresContadorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lexico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lexico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lexico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lexico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lexico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField caracteres;
    private javax.swing.JTextArea comentarios;
    private javax.swing.JComboBox<String> errores;
    private javax.swing.JTextField erroresContador;
    private javax.swing.JTextField espacios;
    private javax.swing.JComboBox<String> especiales;
    private javax.swing.JTextField especialesContador;
    private javax.swing.JTextArea fuenteSinComentarios;
    private javax.swing.JTextArea fuenteSinEspacios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox<String> letras;
    private javax.swing.JComboBox<String> numeros;
    private javax.swing.JTextField numerosContador;
    private javax.swing.JComboBox<String> operadores;
    private javax.swing.JTextField retornos;
    private javax.swing.JComboBox<String> separadores;
    private javax.swing.JTextField tabuladores;
    private javax.swing.JButton volver;
    // End of variables declaration//GEN-END:variables
}
