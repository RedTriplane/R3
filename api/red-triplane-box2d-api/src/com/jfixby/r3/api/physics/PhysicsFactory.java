package com.jfixby.r3.api.physics;

import com.jfixby.r3.api.unit.components.physics.PhysicsCoreDebugRenderer;
import com.jfixby.r3.api.unit.components.physics.PhysicsCoreDebugRendererSpecs;
import com.jfixby.r3.api.unit.components.physics.PhysicsCoreStator;
import com.jfixby.r3.api.unit.components.physics.PhysicsCoreStatorSpecs;

public interface PhysicsFactory {

	PhysicsCoreStator newPhysicsCoreStator(
			PhysicsCoreStatorSpecs stator_properties);

	PhysicsCoreStatorSpecs newPhysicsCoreStatorSpecs();

	PhysicsCoreDebugRendererSpecs newDebugRendererSpecs();

	PhysicsCoreDebugRenderer newDebugRenderer(
			PhysicsCoreDebugRendererSpecs debug_renderer_properties);

}
