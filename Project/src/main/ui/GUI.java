package ui;

import model.Drink;
import model.Event;
import model.EventLog;
import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//GUI that takes order
//Function: add orders, modify the order, remove orders,
// check waitingList and doneList, check each order's information,
// add address when price >= 20, load from and save to Json file
public class GUI extends JFrame implements ActionListener {

    boolean modifying = false; //boolean to see if it's now modifying order or adding new order
    Order selectedOrder; //The order that we're modifying
    Order nowOrder; //The order that is now shown in the order info review

    //Effect : setup the frame
    public GUI() {
        super("Order System");
        main = new TakeOrderGUI();
        drinkCode = -1;
        size = 1;
        orders = new ArrayList<>();
        doneOrders = new ArrayList<>();
        drinks = new ArrayList<>();

        setLeft();
        setRight();
        setBoundOfLeft();
        setBoundOfRight();
        setButtons();
        setListener();
        addToFrame();

        coffeeImage = new ImageIcon(getClass().getResource("coffee.png"));
        imageLabel = new JLabel(coffeeImage);
        this.add(imageLabel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLayout(null);
    }

    //Effect : set up the components on the left half side
    public void setLeft() {
        title = new JLabel("Order System");
        infoText = new JLabel("...");
        readButton = new JButton("Read");
        addOrderButton = new JButton("Add Order");
        removeOrderButton = new JButton("Remove Order");
        saveButton = new JButton("Save");
        quitButton = new JButton("Quit");
        enterButton = new JButton("Enter");
        doneOrderButton = new JButton("Done Order");
        modifyDrinkButton = new JButton("Modify Order");
        modifyDrinkButton.setEnabled(false);
        selectOrderButton = new JButton("Select");
        setTextField();

        drinksRadioButtons = new ArrayList<>();
        setRadioButton();

    }

    //Effect : set up the components on the right half side
    private void setRight() {
        orderWaitingJList = new JList<>();
        doneJList = new JList<>();
        orderWaitingTitle = new JLabel("Order Waiting");
        orderDoneTitle = new JLabel("Order done");
        orderInfoReview = new JTextPane();
        addAddressButton = new JButton("Save address");
        addAddressButton.setEnabled(false);

        orderInfoReview.setContentType("text/html");
        defaultOrderInfoDisplay();
    }

    //Effect : set buttons
    private void setButtons() {
        ArrayList<JButton> buttons = addButtons();
        for (JButton button : buttons) {
            button.setFocusable(false);
            button.setFont(new Font("Arial", Font.BOLD, 11));
            button.setForeground(Color.WHITE);
            button.setBackground(Color.black);
        }
    }

    //Effect : return a list with all the buttons
    private ArrayList<JButton> addButtons() {
        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(readButton);
        buttons.add(addOrderButton);
        buttons.add(removeOrderButton);
        buttons.add(selectOrderButton);
        buttons.add(doneOrderButton);
        buttons.add(modifyDrinkButton);
        buttons.add(enterButton);
        buttons.add(addAddressButton);
        buttons.add(quitButton);
        buttons.add(saveButton);
        return buttons;
    }

    //Effect : set up textFields
    private void setTextField() {

        dateTextField = new JTextField("date (yyyymmdd)");
        customerNameTextField = new JTextField("Customer Name");
        customerCodeTextField = new JTextField("Customer code");
        dateTextField2 = new JTextField("date (yyyymmdd)");
        addressTextField = new JTextField("Address");
        orderInfoTextPane = new JTextPane();
        orderInfoTextPane.setContentType("text/html");
        clearOrderInfo();
    }

    //Effect : set up radio buttons
    public void setRadioButton() {

        americanoRadioButton = new JRadioButton("Americano");
        appleMilkRadioButton = new JRadioButton("Apple milk");
        cappuccinoRadioButton = new JRadioButton("Cappuccino");
        caramelLatteRadioButton = new JRadioButton("Caramel Latte");
        coldBrewRadioButton = new JRadioButton("Cold Brew");
        chocolateMilkRadioButton = new JRadioButton("Chocolate Milk");
        icedTeaRadioButton = new JRadioButton("Iced Tea");
        icedCoffeeRadioButton = new JRadioButton("Iced Coffee");
        latteRadioButton = new JRadioButton("Latte");
        macchiatoRadioButton = new JRadioButton("Macchiatto");
        milkTeaRadioButton = new JRadioButton("Milk Tea");
        mochaRadioButton = new JRadioButton("Mocha");
        shakesRadioButton = new JRadioButton("Shakes");

        addToArrayList();
        sizeSmall = new JRadioButton("Small");
        sizeMedium = new JRadioButton("Medium");
        sizeLarge = new JRadioButton("Large");
    }

    //Effect : put these radio buttons into an array
    public void addToArrayList() {
        drinksRadioButtons.add(americanoRadioButton);
        drinksRadioButtons.add(appleMilkRadioButton);
        drinksRadioButtons.add(cappuccinoRadioButton);
        drinksRadioButtons.add(caramelLatteRadioButton);
        drinksRadioButtons.add(coldBrewRadioButton);
        drinksRadioButtons.add(chocolateMilkRadioButton);
        drinksRadioButtons.add(icedTeaRadioButton);
        drinksRadioButtons.add(icedCoffeeRadioButton);
        drinksRadioButtons.add(latteRadioButton);
        drinksRadioButtons.add(macchiatoRadioButton);
        drinksRadioButtons.add(mochaRadioButton);
        drinksRadioButtons.add(milkTeaRadioButton);
        drinksRadioButtons.add(shakesRadioButton);
    }

    //Effect : set bounds of  the components on the right side
    private void setBoundOfRight() {
        orderWaitingTitle.setBounds(650, 0, 150, 20);
        orderDoneTitle.setBounds(800, 0, 150, 20);
        orderWaitingJList.setBounds(650, 30, 140, 100);
        doneJList.setBounds(800, 30, 140, 100);
        orderInfoReview.setBounds(650, 150, 320, 400);
        addressTextField.setBounds(650, 560, 200, 20);
        addAddressButton.setBounds(865, 560, 115, 20);
    }

    //Effect : set bounds of the components on the left side
    public void setBoundOfLeft() {
        title.setBounds(10, 0, 150, 20);
        infoText.setBounds(150, 0, 450, 20);
        readButton.setBounds(10, 20, 110, 15);
        addOrderButton.setBounds(10, 45, 110, 15);

        modifyDrinkButton.setBounds(240, 325, 110, 15);
        selectOrderButton.setBounds(10, 375, 110, 15);
        removeOrderButton.setBounds(10, 400, 110, 15);
        doneOrderButton.setBounds(130, 400, 110, 15);

        saveButton.setBounds(10, 475, 110, 15);
        quitButton.setBounds(10, 500, 110, 15);
        enterButton.setBounds(515, 240, 85, 20);

        dateTextField.setBounds(150, 20, 200, 20);
        customerNameTextField.setBounds(150, 45, 200, 20);
        customerCodeTextField.setBounds(130, 375, 110, 18);
        dateTextField2.setBounds(150, 475, 200, 20);

        orderInfoTextPane.setBounds(30, 70, 320, 250);
        setBoundOfRadioButton();
    }

    //Effect : set bounds of the radio buttons
    public void setBoundOfRadioButton() {
        americanoRadioButton.setBounds(400, 60, 100, 20);
        appleMilkRadioButton.setBounds(400, 90, 100, 20);
        cappuccinoRadioButton.setBounds(400, 120, 100, 20);
        caramelLatteRadioButton.setBounds(400, 150, 100, 20);
        coldBrewRadioButton.setBounds(400, 180, 100, 20);
        chocolateMilkRadioButton.setBounds(400, 210, 100, 20);
        icedTeaRadioButton.setBounds(400, 240, 100, 20);

        icedCoffeeRadioButton.setBounds(500, 60, 100, 20);
        latteRadioButton.setBounds(500, 90, 100, 20);
        macchiatoRadioButton.setBounds(500, 120, 100, 20);
        milkTeaRadioButton.setBounds(500, 150, 100, 20);
        mochaRadioButton.setBounds(500, 180, 100, 20);
        shakesRadioButton.setBounds(500, 210, 100, 20);

        sizeSmall.setBounds(400, 280, 80, 10);
        sizeMedium.setBounds(400, 300, 80, 10);
        sizeLarge.setBounds(400, 320, 80, 10);
    }

    //Effect : set up listener
    public void setListener() {
        readButton.addActionListener(this);
        addOrderButton.addActionListener(this);
        removeOrderButton.addActionListener(this);
        doneOrderButton.addActionListener(this);
        saveButton.addActionListener(this);
        quitButton.addActionListener(this);
        enterButton.addActionListener(this);
        modifyDrinkButton.addActionListener(this);
        selectOrderButton.addActionListener(this);
        addAddressButton.addActionListener(this);
        for (JRadioButton radioButton : drinksRadioButtons) {
            radioButton.addActionListener(this);
        }
        sizeSmall.addActionListener(this);
        sizeMedium.addActionListener(this);
        sizeLarge.addActionListener(this);

        setJlistListener();
    }

    //Effect : set up listener for Jlist specifically
    private void setJlistListener() {

        orderWaitingJList.addListSelectionListener(e -> {
            int orderNum = orderWaitingJList.getSelectedIndex();
            if (orderNum >= 0) {
                Order order = orders.get(orderNum);
                runOrderInfo(order);
                nowOrder = order;
            }
        });

        doneJList.addListSelectionListener(e -> {
            int orderNum = doneJList.getSelectedIndex();
            if (orderNum >= 0) {
                Order order = doneOrders.get(orderNum);
                runOrderInfo(order);
                nowOrder = null;
            }
        });
    }

    //Effect : add all components onto Jframe
    public void addToFrame() {
        this.add(title);
        this.add(infoText);
        addToFrameLeft();
        for (JRadioButton radioButton : drinksRadioButtons) {
            this.add(radioButton);
        }
        this.add(sizeLarge);
        this.add(sizeMedium);
        this.add(sizeSmall);

        this.add(dateTextField);
        this.add(customerNameTextField);
        this.add(addressTextField);
        this.add(customerCodeTextField);
        this.add(dateTextField2);
        this.add(orderInfoTextPane);
        addToFrameRight();
    }

    //Effect : add components on the left side onto Jframe
    private void addToFrameLeft() {
        this.add(readButton);
        this.add(addOrderButton);
        this.add(selectOrderButton);
        this.add(modifyDrinkButton);
        this.add(removeOrderButton);
        this.add(doneOrderButton);
        this.add(saveButton);
        this.add(quitButton);
        this.add(enterButton);
    }

    //Effect : add components on the right side onto JFrame
    private void addToFrameRight() {
        this.add(orderWaitingTitle);
        this.add(orderDoneTitle);
        this.add(orderWaitingJList);
        this.add(doneJList);
        this.add(orderInfoReview);
        this.add(addAddressButton);
    }

    //Modify : this
    //Effect : assigned action when button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == readButton) {
            readAction();
        } else if (e.getSource() == addOrderButton) {
            addOrderAction();
        } else if (e.getSource() == selectOrderButton) {
            selectOrderAction();
        } else if (e.getSource() == modifyDrinkButton) {
            modifyDrinkAction();
        } else if (e.getSource() == removeOrderButton) {
            removeOrderAction();
        } else if (e.getSource() == doneOrderButton) {
            doneOrderAction();
        } else if (e.getSource() == saveButton) {
            saveAction();
        } else if (e.getSource() == enterButton) {
            enterAction();
        } else if (e.getSource() == addAddressButton) {
            addAddressAction(nowOrder);
        } else if (e.getSource() == quitButton) {
            quitAction();
        } else {
            radioButtonActionListener(e);
        }
    }

    //Effect : assigned action when radio button is clicked
    private void radioButtonActionListener(ActionEvent e) {

        if (e.getSource() == sizeSmall) {
            sizeSmallActionPerformed();
        } else if (e.getSource() == sizeMedium) {
            sizeMediumActionPerformed();
        } else if (e.getSource() == sizeLarge) {
            sizeLargeActionPerformed();
        } else {
            for (JRadioButton drinkSelected : drinksRadioButtons) {
                if (e.getSource() == drinkSelected) {
                    callDrinkAction1(drinkSelected);
                }
            }
        }
    }

    //Modify : this
    //Effect : when select button is clicked, read the code and modifying become true
    // target on the order
    private void selectOrderAction() {
        if (customerCodeTextField.getText() != null) {
            try {
                String code = customerCodeTextField.getText();
                Order targetOrder = main.findOrder(code);
                modifying = true; // remember to set back to false in modifyDrinkAction
                selectedOrder = targetOrder; // remember to set to null in modifyDrink
                previewOrderInfo(selectedOrder);
                addOrderButton.setEnabled(false);
                modifyDrinkButton.setEnabled(true);
            } catch (Exception exception) {
                infoText.setText("Not in waitingList");
            }
        } else {
            infoText.setText("Please include a customer code.");
        }
        customerCodeTextField.setText("Customer code");
    }

    //Effect : when modify order button is clicked, the changes will be stored and modifying will be false
    // de-select the order
    private void modifyDrinkAction() {
        modifying = false;
        selectedOrder = null;
        clearOrderInfo();
        updateBothList();
        addOrderButton.setEnabled(true);
        modifyDrinkButton.setEnabled(false);
        infoText.setText("Order has been modified successfully.");
    }

    //Effect : react to which radio button is clicked
    private void callDrinkAction1(JRadioButton drinkSelected) {

        if (drinkSelected == americanoRadioButton) {
            americanoActionPerformed();
        } else if (drinkSelected == appleMilkRadioButton) {
            appleMilkActionPerformed();
        } else if (drinkSelected == cappuccinoRadioButton) {
            cappuccinoActionPerformed();
        } else if (drinkSelected == caramelLatteRadioButton) {
            caramelLatteActionPerformed();
        } else if (drinkSelected == coldBrewRadioButton) {
            coldBrewActionPerformed();
        } else if (drinkSelected == chocolateMilkRadioButton) {
            chocolateMilkActionPerformed();
        } else {
            callDrinkAction2(drinkSelected);
        }
    }

    //Effect : react to which radio button is clicked
    private void callDrinkAction2(JRadioButton drinkSelected) {
        if (drinkSelected == icedTeaRadioButton) {
            icedTeaActionPerformed();
        } else if (drinkSelected == icedCoffeeRadioButton) {
            icedCoffeeActionPerformed();
        } else if (drinkSelected == latteRadioButton) {
            latteActionPerformed();
        } else if (drinkSelected == macchiatoRadioButton) {
            macchiatoActionPerformed();
        } else if (drinkSelected == milkTeaRadioButton) {
            milkTeaActionPerformed();
        } else if (drinkSelected == mochaRadioButton) {
            mochaActionPerformed();
        } else if (drinkSelected == shakesRadioButton) {
            shakesActionPerformed();
        }
    }

    //Effect : read json file
    public void readAction() {
        String date = dateTextField.getText();
        if (date != null) {
            try {
                orders = main.readJson(date);
                doneOrders = main.getOrderDone();
                readButton.setEnabled(false);
                updateBothList();
                clearOrderInfo();
            } catch (Exception exception) {
                infoText.setText("Cannot Find The Orders in " + date);
            }
        } else {
            infoText.setText("Invalid fileName (yyyymmdd)");
        }
    }

    //Effect : store order to the order list, clear the information in preview area
    public void addOrderAction() {
        if (drinks.size() >= 1) {
            if (!customerNameTextField.getText().equals("Customer Name") && !customerNameTextField.equals("")) {
                orders = main.createOrder(customerNameTextField.getText(), drinks);
                drinks.clear();
                customerNameTextField.setText("Customer Name");
                infoText.setText("Order is added to waitingList.");
                clearOrderInfo();
                updateBothList();
            } else {
                infoText.setText("Please enter customer Name.");
            }
        } else {
            infoText.setText("Please select some drinks first.");
        }
    }

    //Effect : set order to the done list
    public void doneOrderAction() {
        if (customerCodeTextField.getText() != null) {
            try {
                String code = customerCodeTextField.getText();
                Order targetOrder = main.findOrder(code);
                orders = main.doneOrder(targetOrder);
                doneOrders = main.getOrderDone();
                updateBothList();
            } catch (Exception exception) {
                infoText.setText("Not in waitingList");
            }
        } else {
            infoText.setText("Please include a customer code.");
        }
        customerCodeTextField.setText("Customer code");
    }

    //Effect : remove this order
    public void removeOrderAction() {
        if (customerCodeTextField.getText() != null) {
            try {
                String code = customerCodeTextField.getText();
                Order targetOrder = main.findOrder(code);
                main.removeOrder(targetOrder);
                updateBothList();
            } catch (Exception exception) {
                infoText.setText("Not in waitingList");
            }
        } else {
            infoText.setText("Please include a customer code.");
        }
        customerCodeTextField.setText("Customer code");
    }

    //Effect : save to Json File
    public void saveAction() {
        String date = dateTextField2.getText();
        if (date != null) {
            try {
                main.writeJson(date);
            } catch (Exception exception) {
                infoText.setText("Cannot write to file...");
            }
        } else {
            infoText.setText("Please include a date (yyyymmdd).");
        }
    }

    //Effect : When enter button is clicked, based on which radio button is now selected
    // it will get its associated drink code and size
    private void enterAction() {
        if (drinkCode >= 1) {
            if (!modifying) {
                drinks.add(new Drink(drinkCode, size));
                infoText.setText("Drink is added to this order.");
                previewOrderInfo();
            } else {
                selectedOrder.addDrinks(new Drink(drinkCode, size));
                infoText.setText("Drink is added to this order.");
                previewOrderInfo(selectedOrder);
            }
        } else {
            infoText.setText("Select drinks and size first");
        }
    }

    //Effect : read from textField and set the address of the order
    private void addAddressAction(Order order) {
        order.modifyAddress(true, addressTextField.getText());
    }

    //Effect : Terminate this window
    private void quitAction() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString());
        }
        this.dispose();
    }

    //Effect : Update the orders in both waiting and done list
    private void updateBothList() {
        DefaultListModel<Order> orderWaiting = new DefaultListModel<>();
        DefaultListModel<Order> orderDone = new DefaultListModel<>();
        for (Order order : orders) {
            orderWaiting.addElement(order);
        }
        for (Order order : doneOrders) {
            orderDone.addElement(order);
        }
        orderWaitingJList.removeAll();
        orderWaitingJList.setModel(orderWaiting);
        doneJList.removeAll();
        doneJList.setModel(orderDone);
    }

    //Effect : The default Order information in the information field
    private void defaultOrderInfoDisplay() {
        StringBuilder toDisplay = new StringBuilder();
        toDisplay.append("<html>Customer Name: " + "<br/>Customer Code: "
                + "<br/>Total Price: " + "<br/>Deliverable: " + "<br/>DrinkList: <html>");
        orderInfoReview.setText(toDisplay.toString());
    }

    //Effect : display the order's information
    private void runOrderInfo(Order order) {
        StringBuilder toDisplay = new StringBuilder();
        toDisplay.append("<html>Customer Name: " + order.getCustomerName() + "<br/>Customer Code: " + order.getOrderID()
                + "<br/>Total Price: " + order.getTotalPrice() + "<br/>Done: " + order.isDone() + "<br/>Deliverable: "
                + order.isValidForDelivery() + "<br/>DrinkList:");

        ArrayList<Drink> drinksInOrder = order.getDrinkList();
        if (drinksInOrder.size() > 0) {
            for (Drink drink : drinksInOrder) {
                toDisplay.append("<br/" + drink.toString());
            }
        }
        orderInfoReview.setText(toDisplay + "<html>");
        addressTextField.setText(order.getCustomerAddress());
        addAddressButton.setEnabled(order.isValidForDelivery());
    }

    //Effect : Set the Order information back to empty
    private void clearOrderInfo() {
        StringBuilder toDisplay = new StringBuilder();
        toDisplay.append("<html>Customer Name: " + "<br/>Customer Code: " + Order.getTrackID()
                + "<br/>Total Price: 0.0" + "<br/>Deliverable: " + "<br/>DrinkList: None<html>");
        orderInfoTextPane.setText(toDisplay.toString());
    }

    //Effect : Display the order information before adding the order into the waiting list
    private void previewOrderInfo() {
        String customerName = customerNameTextField.getText();
        double totalPrice = 0;
        boolean deliverable = false;
        StringBuilder toDisplay = new StringBuilder();

        if (drinks.size() > 0) {
            for (Drink drink : drinks) {
                totalPrice += drink.getPrice();
            }
        }

        if (totalPrice >= 20) {
            deliverable = true;
        }

        if (customerName.equals("Customer Name")) {
            customerName = "Unknown";
        }

        toDisplay.append("<html>Customer Name: " + customerName + "<br/>Customer Code: " + Order.getTrackID()
                + "<br/>Total Price: " + totalPrice + "<br/>Deliverable: " + deliverable + "<br/>DrinkList:");

        if (drinks.size() > 0) {
            for (Drink drink : drinks) {
                toDisplay.append("<br/" + drink.toString());
            }
        }

        orderInfoTextPane.setText(toDisplay + "<html>");
    }

    //Effect : Display the order information before confirming to modify it
    private void previewOrderInfo(Order thisOrder) {
        String customerName = thisOrder.getCustomerName();
        double totalPrice = thisOrder.getTotalPrice();
        boolean deliverable = thisOrder.isValidForDelivery();
        StringBuilder toDisplay = new StringBuilder();

        toDisplay.append("<html>Customer Name: " + customerName + "<br/>Customer Code: " + thisOrder.getOrderID()
                + "<br/>Total Price: " + totalPrice + "<br/>Deliverable: " + deliverable + "<br/>DrinkList:");

        ArrayList<Drink> thisDrinks = thisOrder.getDrinkList();
        if (thisDrinks.size() > 0) {
            for (Drink drink : thisDrinks) {
                toDisplay.append("<br/" + drink.toString());
            }
        }

        orderInfoTextPane.setText(toDisplay + "<html>");
    }

    // Effect: select this button and set other button to unselect
    private void americanoActionPerformed() {
        if (americanoRadioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != americanoRadioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 1;
        }
    }

    // Effect: select this button and set other button to unselect
    private void appleMilkActionPerformed() {
        if (appleMilkRadioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != appleMilkRadioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 2;
        }
    }

    // Effect: select this button and set other button to unselect
    private void cappuccinoActionPerformed() {
        JRadioButton radioButton = cappuccinoRadioButton;
        if (radioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != radioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 3;
        }
    }

    // Effect: select this button and set other button to unselect
    private void caramelLatteActionPerformed() {
        JRadioButton radioButton = caramelLatteRadioButton;
        if (radioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != radioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 4;
        }
    }

    // Effect: select this button and set other button to unselect
    private void coldBrewActionPerformed() {
        JRadioButton radioButton = coldBrewRadioButton;
        if (radioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != radioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 5;
        }
    }

    // Effect: select this button and set other button to unselect
    private void chocolateMilkActionPerformed() {
        JRadioButton radioButton = chocolateMilkRadioButton;
        if (radioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != radioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 6;
        }
    }

    // Effect: select this button and set other button to unselect
    private void icedTeaActionPerformed() {
        JRadioButton radioButton = icedTeaRadioButton;
        if (radioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != radioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 7;
        }
    }

    // Effect: select this button and set other button to unselect
    private void icedCoffeeActionPerformed() {
        JRadioButton radioButton = icedCoffeeRadioButton;
        if (radioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != radioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 8;
        }
    }

    // Effect: select this button and set other button to unselect
    private void latteActionPerformed() {
        JRadioButton radioButton = latteRadioButton;
        if (radioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != radioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 9;
        }
    }

    // Effect: select this button and set other button to unselect
    private void macchiatoActionPerformed() {
        JRadioButton radioButton = macchiatoRadioButton;
        if (radioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != radioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 10;
        }
    }

    // Effect: select this button and set other button to unselect
    private void milkTeaActionPerformed() {
        JRadioButton radioButton = milkTeaRadioButton;
        if (radioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != radioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 11;
        }
    }

    // Effect: select this button and set other button to unselect
    private void mochaActionPerformed() {
        JRadioButton radioButton = mochaRadioButton;
        if (radioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != radioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 12;
        }
    }

    // Effect: select this button and set other button to unselect
    private void shakesActionPerformed() {
        JRadioButton radioButton = shakesRadioButton;
        if (radioButton.isSelected()) {
            for (JRadioButton otherRadioButton : drinksRadioButtons) {
                if (otherRadioButton != radioButton) {
                    otherRadioButton.setSelected(false);
                }
            }
            drinkCode = 13;
        }
    }

    // Effect: select this button and set other button to unselect
    private void sizeSmallActionPerformed() {
        if (sizeSmall.isSelected()) {
            sizeLarge.setSelected(false);
            sizeMedium.setSelected(false);
            size = 0;
        }
    }

    // Effect: select this button and set other button to unselect
    private void sizeMediumActionPerformed() {
        if (sizeMedium.isSelected()) {
            sizeLarge.setSelected(false);
            sizeSmall.setSelected(false);
            size = 1;
        }
    }

    // Effect: select this button and set other button to unselect
    private void sizeLargeActionPerformed() {
        if (sizeLarge.isSelected()) {
            sizeSmall.setSelected(false);
            sizeMedium.setSelected(false);
            size = 2;
        }
    }


    private JList orderWaitingJList;
    private JList doneJList;
    private JLabel orderWaitingTitle;
    private JLabel orderDoneTitle;
    private JTextPane orderInfoReview;
    private TakeOrderGUI main;
    private int drinkCode;
    private int size;
    private ArrayList<Order> orders;
    private ArrayList<Drink> drinks;
    private ArrayList<Order> doneOrders;
    private JLabel title;
    private JLabel infoText;
    private JButton readButton;
    private JButton addOrderButton;
    private JButton doneOrderButton;
    private JButton removeOrderButton;
    private JButton saveButton;
    private JButton quitButton;
    private JTextField dateTextField;
    private JTextField customerNameTextField;
    private JTextField dateTextField2;
    private JTextField customerCodeTextField;
    private JTextPane orderInfoTextPane;
    private JRadioButton icedTeaRadioButton;
    private JRadioButton mochaRadioButton;
    private JRadioButton latteRadioButton;
    private JRadioButton icedCoffeeRadioButton;
    private JRadioButton milkTeaRadioButton;
    private JRadioButton macchiatoRadioButton;
    private JRadioButton americanoRadioButton;
    private JRadioButton chocolateMilkRadioButton;
    private JRadioButton coldBrewRadioButton;
    private JRadioButton caramelLatteRadioButton;
    private JRadioButton appleMilkRadioButton;
    private JRadioButton cappuccinoRadioButton;
    private JRadioButton shakesRadioButton;
    private JRadioButton sizeSmall;
    private JRadioButton sizeMedium;
    private JRadioButton sizeLarge;
    private JButton enterButton;
    private JButton modifyDrinkButton;
    private JButton selectOrderButton;
    private JTextField addressTextField;
    private JButton addAddressButton;

    private ImageIcon coffeeImage;
    JLabel imageLabel;

    private ArrayList<JRadioButton> drinksRadioButtons;
}
