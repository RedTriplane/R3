package org.jbox2d.d.particle;

import org.jbox2d.d.common.Transform;
import org.jbox2d.d.common.Vector2;

public class ParticleGroup {

  ParticleSystem m_system;
  int m_firstIndex;
  int m_lastIndex;
  int m_groupFlags;
  double m_strength;
  ParticleGroup m_prev;
  ParticleGroup m_next;

  int m_timestamp;
  double m_mass;
  double m_inertia;
  final Vector2 m_center = new Vector2();
  final Vector2 m_linearVelocity = new Vector2();
  double m_angularVelocity;
  final Transform m_transform = new Transform();

  boolean m_destroyAutomatically;
  boolean m_toBeDestroyed;
  boolean m_toBeSplit;

  Object m_userData;

  public ParticleGroup() {
    // m_system = null;
    m_firstIndex = 0;
    m_lastIndex = 0;
    m_groupFlags = 0;
    m_strength = 1.0f;

    m_timestamp = -1;
    m_mass = 0;
    m_inertia = 0;
    m_angularVelocity = 0;
    m_transform.setIdentity();

    m_destroyAutomatically = true;
    m_toBeDestroyed = false;
    m_toBeSplit = false;
  }

  public ParticleGroup getNext() {
    return m_next;
  }

  public int getParticleCount() {
    return m_lastIndex - m_firstIndex;
  }

  public int getBufferIndex() {
    return m_firstIndex;
  }

  public int getGroupFlags() {
    return m_groupFlags;
  }

  public void setGroupFlags(int flags) {
    m_groupFlags = flags;
  }

  public double getMass() {
    updateStatistics();
    return m_mass;
  }

  public double getInertia() {
    updateStatistics();
    return m_inertia;
  }

  public Vector2 getCenter() {
    updateStatistics();
    return m_center;
  }

  public Vector2 getLinearVelocity() {
    updateStatistics();
    return m_linearVelocity;
  }

  public double getAngularVelocity() {
    updateStatistics();
    return m_angularVelocity;
  }

  public Transform getTransform() {
    return m_transform;
  }

  public Vector2 getPosition() {
    return m_transform.p;
  }

  public double getAngle() {
    return m_transform.q.getAngle();
  }

  public Object getUserData() {
    return m_userData;
  }

  public void setUserData(Object data) {
    m_userData = data;
  }
  
  

  public void updateStatistics() {
    if (m_timestamp != m_system.m_timestamp) {
      double m = m_system.getParticleMass();
      m_mass = 0;
      m_center.setZero();
      m_linearVelocity.setZero();
      for (int i = m_firstIndex; i < m_lastIndex; i++) {
        m_mass += m;
        Vector2 pos = m_system.m_positionBuffer.data[i];
        m_center.x += m * pos.x;
        m_center.y += m * pos.y;
        Vector2 vel = m_system.m_velocityBuffer.data[i];
        m_linearVelocity.x += m * vel.x;
        m_linearVelocity.y += m * vel.y;
      }
      if (m_mass > 0) {
        m_center.x *= 1 / m_mass;
        m_center.y *= 1 / m_mass;
        m_linearVelocity.x *= 1 / m_mass;
        m_linearVelocity.y *= 1 / m_mass;
      }
      m_inertia = 0;
      m_angularVelocity = 0;
      for (int i = m_firstIndex; i < m_lastIndex; i++) {
        Vector2 pos = m_system.m_positionBuffer.data[i];
        Vector2 vel = m_system.m_velocityBuffer.data[i];
        double px = pos.x - m_center.x;
        double py = pos.y - m_center.y;
        double vx = vel.x - m_linearVelocity.x;
        double vy = vel.y - m_linearVelocity.y;
        m_inertia += m * (px * px + py * py);
        m_angularVelocity += m * (px * vy - py * vx);
      }
      if (m_inertia > 0) {
        m_angularVelocity *= 1 / m_inertia;
      }
      m_timestamp = m_system.m_timestamp;
    }
  }
}
