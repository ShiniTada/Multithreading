package com.epam.taskthree.main;

import com.epam.taskthree.creator.Creator;
import com.epam.taskthree.entity.Cashier;
import com.epam.taskthree.entity.Customer;
import com.epam.taskthree.repository.Repository;
import com.epam.taskthree.repository.SearchSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private final static Logger LOGGER = LogManager.getLogger(Main.class);
    private final static int AMOUNT_OF_CASHIERS = 3;
    private static Lock MAIN_LOCK = new ReentrantLock();

    public static void main(String[] args) {

        Creator creator = new Creator();

        Repository<Customer> repositoryCustomer = creator.createCustomersFromFileAndAddToRepository("data\\aLotOfOrders.txt");
        List<Customer> ordinaryCustomers = repositoryCustomer.query((new SearchSpecification()).ORDINARY);
        List<Customer> preOrderCustomers = repositoryCustomer.query((new SearchSpecification()).PRE_ORDER);


        List<Cashier> cashiers = divideCustomersAtTheCashiers(ordinaryCustomers);

        List<Future<String>> results = getListOfFutureCashiersResults(cashiers);
        if (!preOrderCustomers.isEmpty()) {
            cashiers.get(1).setSpecialCustomer(preOrderCustomers.get(0));
        }
        try {
            TimeUnit.SECONDS.sleep(10);
            MAIN_LOCK.lock();
            for (int numberOfCushier = 0; numberOfCushier < AMOUNT_OF_CASHIERS; numberOfCushier++) {
                LOGGER.info(numberOfCushier + " " + results.get(numberOfCushier).get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            MAIN_LOCK.unlock();
        }
    }


    private static List<Future<String>> getListOfFutureCashiersResults(List<Cashier> cashiers) {

        ExecutorService service = Executors.newFixedThreadPool(AMOUNT_OF_CASHIERS);
        List<Future<String>> futureResults = new ArrayList<>();

        for (int number = 0; number < AMOUNT_OF_CASHIERS; number++) {
            futureResults.add(service.submit(cashiers.get(number)));
        }
        LOGGER.info("Cashiers give their reports.");
        service.shutdown();
        return futureResults;
    }


    /**
     * Create list of cashiers and divide customers at these cashiers.
     * If the number of people is not divided equally at the cashiers,
     * send the rest of people to the last cashier.
     *
     * @param ordinaryCustomers list of customers
     * @return list of queues(list of people which stay in cashier)
     */
    public static List<List<Customer>> divideCustomersInQueues(List<Customer> ordinaryCustomers) {
        List<List<Customer>> queues = new ArrayList<>();
        int sizeQueue = ordinaryCustomers.size() / AMOUNT_OF_CASHIERS;

        int firstPosition = 0, lastPosition = sizeQueue;
        for (int number = 0; number < AMOUNT_OF_CASHIERS - 1; number++) {
            queues.add(ordinaryCustomers.subList(firstPosition, lastPosition));
            if (number != AMOUNT_OF_CASHIERS - 2) {
                firstPosition += sizeQueue;
                lastPosition += sizeQueue;
            }
        }
        firstPosition += sizeQueue;
        lastPosition = ordinaryCustomers.size();
        queues.add(ordinaryCustomers.subList(firstPosition, lastPosition));
        LOGGER.info("Customers are divided in queues");
        return queues;
    }


    /**
     * Call {@link #divideCustomersInQueues} and add to each
     * cashier one queue until the cashiers or queues are over.
     *
     * @param ordinaryCustomers list of customers
     * @return list of cashiers with queues
     */
    public static List<Cashier> divideCustomersAtTheCashiers(List<Customer> ordinaryCustomers) {
        List<List<Customer>> queues = divideCustomersInQueues(ordinaryCustomers);

        List<Cashier> filledCashiers = new ArrayList<>();
        int numberCashier = 1, numberQueue = 0;
        while (numberCashier <= AMOUNT_OF_CASHIERS && numberQueue < queues.size()) {
            Cashier cashier = new Cashier(queues.get(numberQueue));
            filledCashiers.add(cashier);
            LOGGER.info("Cashier is added.");
            numberCashier++;
            numberQueue++;
        }
        LOGGER.info("Queues is added at cashiers.");
        return filledCashiers;
    }


}
