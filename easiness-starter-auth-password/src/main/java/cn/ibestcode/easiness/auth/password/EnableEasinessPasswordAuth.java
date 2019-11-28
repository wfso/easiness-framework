package cn.ibestcode.easiness.auth.password;

import cn.ibestcode.easiness.auth.EnableEasinessAuth;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author wfso (仵士杰)
 * #-------------------------------------------------------------------------------------------------------------#
 * #               _____                    _____                    _____                   _______             #
 * #              /\    \                  /\    \                  /\    \                 /::\    \            #
 * #             /::\____\                /::\    \                /::\    \               /::::\    \           #
 * #            /:::/    /               /::::\    \              /::::\    \             /::::::\    \          #
 * #           /:::/   _/___            /::::::\    \            /::::::\    \           /::::::::\    \         #
 * #          /:::/   /\    \          /:::/\:::\    \          /:::/\:::\    \         /:::/~~\:::\    \        #
 * #         /:::/   /::\____\        /:::/__\:::\    \        /:::/__\:::\    \       /:::/    \:::\    \       #
 * #        /:::/   /:::/    /       /::::\   \:::\    \       \:::\   \:::\    \     /:::/    / \:::\    \      #
 * #       /:::/   /:::/   _/___    /::::::\   \:::\    \    ___\:::\   \:::\    \   /:::/____/   \:::\____\     #
 * #      /:::/___/:::/   /\    \  /:::/\:::\   \:::\    \  /\   \:::\   \:::\    \ |:::|    |     |:::|    |    #
 * #     |:::|   /:::/   /::\____\/:::/  \:::\   \:::\____\/::\   \:::\   \:::\____\|:::|____|     |:::|    |    #
 * #     |:::|__/:::/   /:::/    /\::/    \:::\   \::/    /\:::\   \:::\   \::/    / \:::\    \   /:::/    /     #
 * #      \:::\/:::/   /:::/    /  \/____/ \:::\   \/____/  \:::\   \:::\   \/____/   \:::\    \ /:::/    /      #
 * #       \::::::/   /:::/    /            \:::\    \       \:::\   \:::\    \        \:::\    /:::/    /       #
 * #        \::::/___/:::/    /              \:::\____\       \:::\   \:::\____\        \:::\__/:::/    /        #
 * #         \:::\__/:::/    /                \::/    /        \:::\  /:::/    /         \::::::::/    /         #
 * #          \::::::::/    /                  \/____/          \:::\/:::/    /           \::::::/    /          #
 * #           \::::::/    /                                     \::::::/    /             \::::/    /           #
 * #            \::::/    /                                       \::::/    /               \::/____/            #
 * #             \::/____/                                         \::/    /                 ~~                  #
 * #              ~~                                                \/____/                                      #
 * #-------------------------------------------------------------------------------------------------------------#
 * create by WFSO (仵士杰) at 2019/7/1 19:38
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EasinessPasswordAuthConfiguration.class})
@EnableEasinessAuth
public @interface EnableEasinessPasswordAuth {
}