package com.cedricmartens.hectogon.client.core.util;

import com.cedricmartens.hectogon.client.core.game.manager.GameManager;
import com.cedricmartens.hectogon.client.core.game.player.DefaultInput;
import com.cedricmartens.hectogon.client.core.game.player.InputService;

public class ServiceUtil
{
    private GameManager gameManager;
    private static ServiceUtil serviceUtil;
    private static InputService inputService;
    private static ItemUtil itemUtil;

    private ServiceUtil() {
    }

    public void updateServices(GameManager gameManager)
    {
        this.gameManager = gameManager;
        ItemUtil.createItemUtil(gameManager.i18NBundle);
        itemUtil = ItemUtil.getItemUtil();
        initializeInputService();
    }

    private static void initializeInputService()
    {
        //TODO check if there's an input config file if not use default
        inputService = new DefaultInput();
    }

    public InputService getInputService()
    {
        return inputService;
    }



    public static ServiceUtil getServiceUtil()
    {
        if(serviceUtil == null)
        {
            serviceUtil = new ServiceUtil();
        }
        return serviceUtil;
    }

    public GameManager getGameManager()
    {
        return gameManager;
    }
}
