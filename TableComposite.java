package org.eclipse.widgets;


import Model.Student;
import Model.info;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import Controller.Controller;

import static java.lang.String.valueOf;

public class TableComposite extends Composite {
    public Table table = new Table(this, SWT.SINGLE | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
    public Label countItems = new Label(this, SWT.NONE);
    public Label allItems = new Label(this, SWT.NONE);
    public Label currentPage = new Label(this, SWT.NONE);
    public Label allPages = new Label(this, SWT.NONE);
    public int currentPages=1;
    private int count = 0;
    private int metka = 0;
    private int countItemOnThePage = 0;

    public void incrementCurrentPages(){
        currentPages++;
    }

    public void reduceCurrentPages(){
        currentPages--;
    }

    public TableComposite(Composite composite, int i) {
        super(composite, i);
    }

    public void draw(int start, int end, info info, Controller controller, int count, int metka) {
        this.count = count;
        this.metka = metka;
        for (Student student : info.getStudents().subList(start, end)) {
            TableItem tableItem = new TableItem(table, SWT.PUSH);
            tableItem.setText(1, String.valueOf(student.getDate()));
            tableItem.setText(3, student.getSurname() + " " + student.getName() + " " + student.getPatronymic());
            tableItem.setText(0, student.gettournamentname());
            tableItem.setText(5, String.valueOf(controller.countEarningsWinner(student)));
            tableItem.setText(2, String.valueOf(student.getsportname()));
            tableItem.setText(4, String.valueOf(student.getPrizeAmount()));
        }

        countItems.setText(table.getItems().length + " items on the page");
        if (table.getItems().length == 0) {
            allPages.setText("There are " + 1 + " pages at all");
        } else {
            allPages.setText("There are " + info.getStudents().size() / table.getItems().length + " pages at all");
        }
        allItems.setText(info.getStudents().size() + " items at all");
        if (table.getItems().length == 0) {
            currentPage.setText("current page is " + 1);
        } else {
            currentPage.setText("current page is " + currentPages);
        }
    }

    public void draw(int start, int end, info info, Controller controller) {
        for (Student student : info.getStudents().subList(start, end)) {
            TableItem tableItem = new TableItem(table, SWT.PUSH);
            tableItem.setText(1, String.valueOf(student.getDate()));
            tableItem.setText(3, student.getSurname() + student.getName() + student.getPatronymic());
            tableItem.setText(0, String.valueOf(student.gettournamentname()));
            tableItem.setText(4, String.valueOf(student.getPrizeAmount()));
            tableItem.setText(2, student.getsportname());
            tableItem.setText(5, String.valueOf(controller.countEarningsWinner(student)));
        }
    }

    public void initTable(info info, Controller controller) {
        allPages.setBounds(700, 500, 120, 30);
        allPages.setText("There are " + 1 + " pages");
        currentPage.setBounds(500, 500, 120, 30);
        allItems.setText(info.getStudents().size() + " items at all");
        allItems.setBounds(300, 500, 120, 30);
        countItems.setText(countItemOnThePage + " items on the page");
        countItems.setBounds(100, 500, 120, 30);
        table.setBounds(50, 50, 892, 300);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        Button nextPage = new Button(this, SWT.PUSH);
        nextPage.setText("next page");
        nextPage.setBounds(848, 360, 100, 30);

        Button prevPage = new Button(this, SWT.PUSH);
        prevPage.setText("previous page");
        prevPage.setBounds(728, 360, 100, 30);

        Button lastPage = new Button(this, SWT.PUSH);
        lastPage.setText("Last page");
        lastPage.setBounds(848, 450, 100, 30);

        Button firstPage = new Button(this, SWT.PUSH);
        firstPage.setText("first page");
        firstPage.setBounds(728, 450, 100, 30);

        Text countPages = new Text(this, SWT.CHECK);
        countPages.setBounds(50, 400, 100, 30);
        Label countPagesText = new Label(this, SWT.NONE);
        countPagesText.setText("Input count of elements");
        countPagesText.setBounds(160, 408, 200, 30);

        TableColumn fioColumn = new TableColumn(table, SWT.LEFT);
        fioColumn.setText("Tournament Name");
        fioColumn.setResizable(true);
        fioColumn.setWidth(150);

        Button generate = new Button(this, SWT.PUSH);
        generate.setText("generate");
        generate.setBounds(50, 450, 100, 30);

        TableColumn dateColumn = new TableColumn(table, SWT.CENTER);
        dateColumn.setText("Date");
        dateColumn.setResizable(true);
        dateColumn.setWidth(150);

        TableColumn studentsMisses = new TableColumn(table, SWT.RIGHT);
        studentsMisses.setText("Sport Name");
        studentsMisses.setResizable(true);
        studentsMisses.setWidth(150);

        TableColumn studentsMisses1 = new TableColumn(table, SWT.RIGHT);
        studentsMisses1.setText("winner's FIO");
        studentsMisses1.setResizable(true);
        studentsMisses1.setWidth(150);

        TableColumn PrizeAmount = new TableColumn(table, SWT.RIGHT);
        PrizeAmount.setText("Prize Amount");
        PrizeAmount.setResizable(true);
        PrizeAmount.setWidth(130);

        TableColumn studentsMisses3 = new TableColumn(table, SWT.RIGHT);
        studentsMisses3.setText("Earnings Winner");
        studentsMisses3.setResizable(true);
        studentsMisses3.setWidth(140);

        nextPage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {

                if (currentPages < info.getStudents().size() / table.getItems().length) {
                    incrementCurrentPages();
                    table.removeAll();
                    draw(info.getMetka(), info.getMetka() + count, info, controller, count, metka);
                }
            }
        });

        prevPage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {

                if (currentPages > 1) {
                    reduceCurrentPages();
                    table.removeAll();
                    draw(info.getMetka() - count, info.getMetka(), info, controller, count, metka);
                }

            }
        });

        lastPage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                currentPages = info.getStudents().size() / table.getItems().length;
                table.removeAll();
                draw(info.getStudents().size() - count, info.getStudents().size(), info, controller, count, metka);
            }
        });

        firstPage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                currentPages = 1;
                table.removeAll();
                draw(0, count, info, controller, count, metka);
            }
        });


        generate.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!countPages.getText().isEmpty()) {
                    table.removeAll();
                    count = Integer.parseInt(countPages.getText());
                    if (count <= info.getStudents().size()) {
                        draw(0, count, info, controller, count, metka);
                    }
                    controller.setGenerate(true);
                }
            }
        });
        currentPage.setText("current page is " + 1);
    }

    public void clear() {
        table.removeAll();
    }
}
