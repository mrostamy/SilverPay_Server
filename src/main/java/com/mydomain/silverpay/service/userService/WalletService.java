package com.mydomain.silverpay.service.userService;


import com.mydomain.silverpay.data.model.mainDB.Wallet;
import com.mydomain.silverpay.repository.MainDB.IWalletRepository;
import com.mydomain.silverpay.data.model.ReturnMessage;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    final IWalletRepository walletRepository;

    public WalletService(IWalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public boolean checkInventory(int amount, String walletId) {

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        if (wallet != null) {

            if (wallet.getInventory() > amount) {
                return true;
            } else {
                return false;
            }


        } else {
            return false;
        }

    }


    public ReturnMessage decrease(int amount, String walletId, boolean isFactor) {


        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        if (wallet != null) {
            if (checkInventory(amount, walletId)) {

                wallet.setInventory(wallet.getInventory() + amount);

                if (isFactor)
                    wallet.setEnterMoney(wallet.getEnterMoney() - amount);

//                wallet.setExitMoney(wallet.getExitMoney() + amount);
                Wallet resWallet = walletRepository.save(wallet);

                if (wallet == null) {

                    return new ReturnMessage(false, "error in decrease inventory", "error");
                }

                return new ReturnMessage(true, null, "success");


            } else {
                return new ReturnMessage(false, "no enough money", "error");
            }
        } else {
            return new ReturnMessage(false, "no wallet found", "error");
        }
    }

    public ReturnMessage increase(int amount, String walletId, boolean isFactor) {

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        if (wallet != null) {

            wallet.setInventory(wallet.getInventory() + amount);

            if (isFactor)
                wallet.setEnterMoney(wallet.getEnterMoney() + amount);

            Wallet resWallet = walletRepository.save(wallet);

            if (resWallet == null) {

                return new ReturnMessage(false, "error in increase inventory", "error");
            }

            return new ReturnMessage(true, null, "success");

        } else {
            return new ReturnMessage(false, "no wallet found", "error");
        }


    }

    public ReturnMessage entryIncreaseInventory(int amount, String walletId) {

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        if (wallet != null) {

            wallet.setInventory(wallet.getInventory() + amount);
            wallet.setExitMoney(wallet.getExitMoney() - amount);
            wallet.setOnExitMoney(wallet.getOnExitMoney() + amount);

            Wallet resWallet = walletRepository.save(wallet);

            if (wallet == null) {

                return new ReturnMessage(false, "error in increase inventory", "error");
            }

            return new ReturnMessage(true, null, "success");

        } else {
            return new ReturnMessage(false, "no wallet found", "error");
        }


    }

    public ReturnMessage entryDecreaseInventory(int amount, String walletId) {

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        if (wallet != null) {

            wallet.setInventory(wallet.getInventory() - amount);
            wallet.setExitMoney(wallet.getExitMoney() + amount);
            wallet.setOnExitMoney(wallet.getOnExitMoney() - amount);
            Wallet resWallet = walletRepository.save(wallet);

            if (wallet == null) {

                return new ReturnMessage(false, "error in increase inventory", "error");
            }

            return new ReturnMessage(true, null, "success");

        } else {
            return new ReturnMessage(false, "no wallet found", "error");
        }


    }






    public ReturnMessage entryIncreaseOnExitMoney(int amount, String walletId) {

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        if (wallet != null) {

            wallet.setOnExitMoney(wallet.getOnExitMoney() + amount);

            Wallet resWallet = walletRepository.save(wallet);

            if (resWallet == null) {

                return new ReturnMessage(false, "error in increase inventory", "error");
            }

            return new ReturnMessage(true, null, "success");

        } else {
            return new ReturnMessage(false, "no wallet found", "error");
        }


    }

    public ReturnMessage entryDecreaseOnExitMoney(int amount, String walletId) {

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        if (wallet != null) {

            wallet.setOnExitMoney(wallet.getOnExitMoney() - amount);

            Wallet resWallet = walletRepository.save(wallet);

            if (resWallet == null) {

                return new ReturnMessage(false, "error in increase inventory", "error");
            }

            return new ReturnMessage(true, null, "success");

        } else {
            return new ReturnMessage(false, "no wallet found", "error");
        }


    }



}
