package com.insurePro.policy_service.Configuarations;

import com.insurePro.policy_service.Enums.PolicyEvents;
import com.insurePro.policy_service.Enums.PolicyStates;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class PolicyStateMachine extends EnumStateMachineConfigurerAdapter<PolicyStates, PolicyEvents> {
    @Override
    public void configure(StateMachineStateConfigurer<PolicyStates, PolicyEvents> states) throws Exception {
        states
                .withStates()
                .initial(PolicyStates.DRAFT)
                .states(EnumSet.allOf(PolicyStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<PolicyStates, PolicyEvents> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(PolicyStates.DRAFT)
                    .target(PolicyStates.ACTIVE)
                    .event(PolicyEvents.SUBMIT)
                .and()
                .withExternal()
                .source(PolicyStates.DRAFT)
                .target(PolicyStates.CANCELLED)
                .event(PolicyEvents.CANCEL)
                .and()
                .withExternal()
                    .source(PolicyStates.ACTIVE)
                    .target(PolicyStates.CANCELLED)
                    .event(PolicyEvents.CANCEL);
    }


    @Override
    public void configure(StateMachineConfigurationConfigurer<PolicyStates, PolicyEvents> config) throws Exception {
        config.withConfiguration().autoStartup(true);
    }




}
