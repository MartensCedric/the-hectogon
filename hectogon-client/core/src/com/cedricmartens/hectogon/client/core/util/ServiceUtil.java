package com.cedricmartens.hectogon.client.core.util;

import com.cedricmartens.hectogon.client.core.game.player.DefaultInput;
import com.cedricmartens.hectogon.client.core.game.player.InputService;

public class ServiceUtil
{
    private static ServiceUtil serviceUtil;
    private static InputService inputService;
    private ServiceUtil() {
        updateServices();
    }

    public void updateServices()
    {
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
}
