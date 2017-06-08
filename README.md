# APIGateway

#### 相关说明
</br>
*API网关*
*APICore: API核心类，实现request的传递参数解析，并调用其映射的目标方法*</br>
*APIMapping: API映射注解类，通过values方法，绑定key与value值*</br>
*APIStore: API存储类，通过扫描Spring容器中的Service，找到所有绑定APIMapping方法，将其存入Map中*</br>
*APIGateway: 执行API调用*
